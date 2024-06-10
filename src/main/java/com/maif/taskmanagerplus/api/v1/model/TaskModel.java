package com.maif.taskmanagerplus.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Relation(collectionRelation = "provinces")
@Setter
@Getter
public class TaskModel extends RepresentationModel<TaskModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Task Title")
	private String title;
	
	@ApiModelProperty(example = "Detailed description of the task.")
	private String description;

	@ApiModelProperty(example = "2024-12-31")
	private OffsetDateTime dueDate;

	@ApiModelProperty(example = "false")
	private Boolean completed;

//	@ApiModelProperty(example = "2024-06-10T12:34:56.789Z")
//	private Date createdAt;
//
//	@ApiModelProperty(example = "2024-06-10T12:34:56.789Z")
//	private Date updatedAt;
	
}
