package com.maif.taskmanagerplus.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import com.maif.taskmanagerplus.api.exceptionhandler.Problem;
import com.maif.taskmanagerplus.api.v1.model.TaskModel;
import com.maif.taskmanagerplus.api.v1.model.input.TaskInput;
import com.maif.taskmanagerplus.domain.filter.TaskFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Tasks")
public interface TaskControllerOpenApi {

	@ApiOperation("List the tasks")
	PagedModel<TaskModel> list(TaskFilter filter, Pageable pageable);
	
	@ApiOperation("List the tasks without pagination")
	CollectionModel<TaskModel> listNoPagination(TaskFilter filter);

	@ApiOperation("Find the task by ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID of task invalid", response = Problem.class),
		@ApiResponse(code = 404, message = "Task not found", response = Problem.class)
	})
	TaskModel find(
			@ApiParam(value = "ID of task", example = "1", required = true)
			Long taskId);

	@ApiOperation("Register a task")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Task registered"),
	})
	TaskModel add(
			@ApiParam(name = "body", value = "Representation of the new task", required = true)
			TaskInput taskInput);

	@ApiOperation("Update the task by ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Task updated"),
		@ApiResponse(code = 404, message = "Task not found", response = Problem.class)
	})
	TaskModel update(
			@ApiParam(value = "ID of the task", example = "1", required = true)
			Long taskId,
			
			@ApiParam(name = "body", value = "Representation of the task with new data", required = true)
			TaskInput taskInput);

	@ApiOperation("Remove the task by ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Task removed"),
		@ApiResponse(code = 404, message = "Task not found", response = Problem.class)
	})
	void remove(
			@ApiParam(value = "ID of the task", example = "1", required = true)
			Long taskId);

}