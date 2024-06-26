package com.maif.taskmanagerplus.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.maif.taskmanagerplus.domain.exception.BusinessException;
import com.maif.taskmanagerplus.domain.exception.EntityInUseException;
import com.maif.taskmanagerplus.domain.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String MSG_ERROR_GENERIC_FINAL_USER 
		= "An unexpected internal system error has occurred. "
				+ "Please try again and if the problem persists, contact your system administrator";
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
		ProblemType problemType = ProblemType.ERROR_SYSTEM;
		String detail = MSG_ERROR_GENERIC_FINAL_USER;

		log.error(ex.getMessage(), ex);
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
    @ExceptionHandler(MysqlDataTruncation.class)
    public ResponseEntity<Object> handleMysqlDataTruncation(MysqlDataTruncation ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String detail = "Data too long for column. " + ex.getMessage() + ". Please shorten the data and try again.";

        Problem problem = createProblemBuilder(status, ProblemType.MAX_LENGTH, detail)
                .userMessage(MSG_ERROR_GENERIC_FINAL_USER)
                .build();
        
        System.out.println(problem);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
	
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof MysqlDataTruncation) {
            return handleMysqlDataTruncation((MysqlDataTruncation) rootCause, request);
        }
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = "Data integrity violation occurred. Please contact support.";

        Problem problem = createProblemBuilder(status, ProblemType.MAX_LENGTH, detail)
                .userMessage(MSG_ERROR_GENERIC_FINAL_USER)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    
    
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = String.format("Resource %s you tried to access is non-existent.", 
				ex.getRequestURL());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERROR_GENERIC_FINAL_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.INVALID_PARAMETER;

		String detail = String.format("URL parameter '%s' received value '%s', "
				+ "which is of an invalid type. Correct and enter a value compatible with type %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERROR_GENERIC_FINAL_USER)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof DateTimeParseException) {
	        DateTimeParseException dtpe = (DateTimeParseException) rootCause;
	        String invalidValue = dtpe.getParsedString();
	        String message = "Failed to parse date value '" + invalidValue + "'. Please use ISO-8601 format (e.g., 'yyyy-MM-dd'T'HH:mm:ss'Z').";

	        ProblemType problemType = ProblemType.INVALID_REQUEST_BODY;
	        String detail = "Failed to parse date value in request body. Check date format and try again.";

	        Problem problem = createProblemBuilder(status, problemType, detail)
	                .userMessage(message)
	                .build();

	        return handleExceptionInternal(ex, problem, headers, status, request);
	    }
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request); 
		}
		
		ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		String detail = "The request body is invalid. Check syntax error.";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERROR_GENERIC_FINAL_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		String detail = String.format("Property '%s' does not exist."
				+ "Correct or remove this property and try again.", path);

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERROR_GENERIC_FINAL_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		String detail = String.format("Property '%s' has been given value '%s', "
				+ "which is of an invalid type. Correct and enter a value compatible with type %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERROR_GENERIC_FINAL_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(AccessDeniedException ex, WebRequest request) {

		HttpStatus status = HttpStatus.FORBIDDEN;
		ProblemType problemType = ProblemType.ACCESS_DENIED;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.userMessage("You do not have permission to perform this operation.")
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntityNotFoundException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handleEntidadeInUse(EntityInUseException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTITY_IN_USE;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleNegocio(BusinessException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERROR_BUSINESS;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
				.timestamp(OffsetDateTime.now())
				.title(status.getReasonPhrase())
				.status(status.value())
				.userMessage(MSG_ERROR_GENERIC_FINAL_USER)
				.build();
		} else if (body instanceof String) {
			body = Problem.builder()
				.timestamp(OffsetDateTime.now())
				.title((String) body)
				.status(status.value())
				.userMessage(MSG_ERROR_GENERIC_FINAL_USER)
				.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
			ProblemType problemType, String detail) {
		
		return Problem.builder()
			.timestamp(OffsetDateTime.now())
			.status(status.value())
			.type(problemType.getUri())
			.title(problemType.getTitle())
			.detail(detail);
	}

	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}
	
	// begin
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
		
        StringBuilder sb = new StringBuilder();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
        	//sb.append(ex.getBindingResult().getFieldError().getField());
        	//sb.append(" ");
            sb.append(error.getDefaultMessage());
        }
		
		ProblemType problemType = ProblemType.ERROR_FORMAT;
		
		Problem problem = createProblemBuilder(status, problemType, sb.toString())
				.userMessage(sb.toString())
				.build();
	    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	// end
}
