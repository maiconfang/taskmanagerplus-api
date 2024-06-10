package com.maif.taskmanagerplus.api.v1.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskInput {

	@ApiModelProperty(example = "1", required = true)
	private Long id;
	
    @ApiModelProperty(example = "Task Title", required = true)
    @NotNull
    private String title;

    @ApiModelProperty(example = "Task Description")
    private String description;

    @ApiModelProperty(example = "2024-06-10")
    private OffsetDateTime dueDate;

    @ApiModelProperty(example = "false", required = true)
    @NotNull
    private Boolean completed;
	
	
	
}
