package com.maif.taskmanagerplus.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import com.maif.taskmanagerplus.api.exceptionhandler.Problem;
import com.maif.taskmanagerplus.api.v1.model.ProvinceModel;
import com.maif.taskmanagerplus.api.v1.model.input.ProvinceInput;
import com.maif.taskmanagerplus.domain.filter.ProvinceFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Provinces")
public interface ProvinceControllerOpenApi {

	@ApiOperation("List the provinces")
	PagedModel<ProvinceModel> list(ProvinceFilter filter, Pageable pageable);
	
	@ApiOperation("List the provinces without pagination")
	CollectionModel<ProvinceModel> listNoPagination(ProvinceFilter filter);

	@ApiOperation("Find the province by ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID of province invalid", response = Problem.class),
		@ApiResponse(code = 404, message = "Province not found", response = Problem.class)
	})
	ProvinceModel find(
			@ApiParam(value = "ID of province", example = "1", required = true)
			Long provinceId);

	@ApiOperation("Register a province")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Province registered"),
	})
	ProvinceModel add(
			@ApiParam(name = "body", value = "Representation of the new province", required = true)
			ProvinceInput provinceInput);

	@ApiOperation("Update the province by ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Province updated"),
		@ApiResponse(code = 404, message = "Province not found", response = Problem.class)
	})
	ProvinceModel update(
			@ApiParam(value = "ID of the province", example = "1", required = true)
			Long provinceId,
			
			@ApiParam(name = "body", value = "Representation of the province with new data", required = true)
			ProvinceInput provinceInput);

	@ApiOperation("Remove the province by ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Province removed"),
		@ApiResponse(code = 404, message = "Province not found", response = Problem.class)
	})
	void remove(
			@ApiParam(value = "ID of the province", example = "1", required = true)
			Long provinceId);

}