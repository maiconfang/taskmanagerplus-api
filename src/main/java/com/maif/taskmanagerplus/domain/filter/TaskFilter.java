package com.maif.taskmanagerplus.domain.filter;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskFilter {

	@ApiModelProperty(example = "1", value = "ID of the task")
    private Long taskId;

    @ApiModelProperty(example = "Task Title", value = "Title of the task")
    private String title;

    @ApiModelProperty(example = "Detailed description of the task.", value = "Description of the task")
    private String description;

    @ApiModelProperty(example = "2024-12-31", value = "Due date of the task")
    private LocalDate dueDate;

    @ApiModelProperty(example = "false", value = "Completion status of the task")
    private Boolean completed;

    @ApiModelProperty(example = "2024-06-10T12:34:56.789Z", value = "Creation date of the task")
    private String createdAt;

    @ApiModelProperty(example = "2024-06-10T12:34:56.789Z", value = "Last update date of the task")
    private String updatedAt;
	
	
}
