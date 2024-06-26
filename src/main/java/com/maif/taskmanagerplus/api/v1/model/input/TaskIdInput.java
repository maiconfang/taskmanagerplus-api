package com.maif.taskmanagerplus.api.v1.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskIdInput {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;
	
	
}
