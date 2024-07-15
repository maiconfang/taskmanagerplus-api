package com.maif.taskmanagerplus.api.v1.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.maif.taskmanagerplus.api.v1.assembler.TaskInputDisassembler;
import com.maif.taskmanagerplus.api.v1.assembler.TaskModelAssembler;
import com.maif.taskmanagerplus.api.v1.model.TaskModel;
import com.maif.taskmanagerplus.api.v1.model.input.TaskInput;
import com.maif.taskmanagerplus.api.v1.openapi.controller.TaskControllerOpenApi;
import com.maif.taskmanagerplus.core.data.PageWrapper;
import com.maif.taskmanagerplus.core.data.PageableTranslator;
import com.maif.taskmanagerplus.core.security.CheckSecurity;
import com.maif.taskmanagerplus.domain.filter.TaskFilter;
import com.maif.taskmanagerplus.domain.model.Task;
import com.maif.taskmanagerplus.domain.repository.TaskRepository;
import com.maif.taskmanagerplus.domain.service.RegisterTaskService;
import com.maif.taskmanagerplus.infrastructure.repository.spec.TaskSpecs;

@RestController
@RequestMapping(path = "/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController implements TaskControllerOpenApi {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private RegisterTaskService registerTask;
	
	@Autowired
	private TaskModelAssembler taskModelAssembler;
	
	@Autowired
	private TaskInputDisassembler taskInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Task> pagedResourcesAssembler;
	
	
	@CheckSecurity.Tasks.CanConsult
	@Override
	@GetMapping
	public PagedModel<TaskModel> list(TaskFilter filter, Pageable pageable) {
		
		Pageable pageableTranslate = translatePageable(pageable);
		Page<Task> tasksPage = null;
		
		if(filter.getTaskId()!=null || filter.getTitle()!=null || filter.getDescription()!=null || filter.getDueDate()!=null || filter.getCompleted()!=null ) {
			tasksPage = taskRepository.findAll(TaskSpecs.withFilter(filter), pageableTranslate);
		}
		else
			tasksPage = taskRepository.findAll(pageable);
		
		tasksPage = new PageWrapper<>(tasksPage, pageable);
		
		return pagedResourcesAssembler.toModel(tasksPage, taskModelAssembler);
	}
	
	@CheckSecurity.Tasks.CanConsult
	@GetMapping("/noPagination")
	public CollectionModel<TaskModel> listNoPagination(TaskFilter filter) {
		
		List<Task> allTasks;
		
		if(filter.getTitle()!=null || filter.getDescription()!=null ) {
			allTasks = taskRepository.findAll(TaskSpecs.withFilter(filter));
		}
		else
			allTasks = taskRepository.findAll();
		
		return taskModelAssembler.toCollectionModel(allTasks);
	}
	
	@CheckSecurity.Tasks.CanConsult
	@Override
	@GetMapping("/{taskId}")
	public TaskModel find(@PathVariable Long taskId) {
		Task task = registerTask.findOrFail(taskId);
		
		return taskModelAssembler.toModel(task);
	}
	
	@CheckSecurity.Tasks.CanEdit
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TaskModel add(@RequestBody @Valid TaskInput taskInput) {
		Task task = taskInputDisassembler.toDomainObject(taskInput);
		
		task = registerTask.save(task);
		
		return taskModelAssembler.toModel(task);
	}
	
	@CheckSecurity.Tasks.CanEdit
	@Override
	@PutMapping("/{taskId}")
	public TaskModel update(@PathVariable Long taskId, @RequestBody @Valid TaskInput taskInput) {
		Task currentTask = registerTask.findOrFail(taskId);
		
		taskInputDisassembler.copyToDomainObject(taskInput, currentTask);
		
		currentTask = registerTask.save(currentTask);
		
		return taskModelAssembler.toModel(currentTask);
	}
	
	@CheckSecurity.Tasks.CanEdit
	@Override
	@DeleteMapping("/{taskId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long taskId) {
		
		Task task = registerTask.findOrFail(taskId);
		String nameTask = task.getTitle();
		
		registerTask.delete(taskId, nameTask);	
	}
	
	private Pageable translatePageable(Pageable apiPageable) {
		var mapping = Map.of(
				"id", "code",
				"name", "name"
			);
		
		return PageableTranslator.translate(apiPageable, mapping);
	}
	
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }
	
}