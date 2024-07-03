package com.maif.taskmanagerplus.api.v1.model.input;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
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

    // It serves to store the due date (or deadline, expire) of the task.
    // It serves to store the expiration date (or deadline) of the task.
    @ApiModelProperty(example = "2024-06-10")
    private LocalDate dueDate;

    @ApiModelProperty(example = "false", required = true)
    @NotNull
    private Boolean completed;
	
	
	
}
