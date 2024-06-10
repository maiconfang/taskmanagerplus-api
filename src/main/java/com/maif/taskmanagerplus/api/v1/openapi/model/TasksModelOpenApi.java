package com.maif.taskmanagerplus.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.maif.taskmanagerplus.api.v1.model.TaskModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("TasksModel")
@Data
public class TasksModelOpenApi {

	private TasksEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("TasksEmbeddedModel")
	@Data
	public class TasksEmbeddedModelOpenApi {
		
		private List<TaskModel> tasks;
		
	}
	
}
