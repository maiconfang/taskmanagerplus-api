package com.maif.taskmanagerplus.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordInput {
	
	@ApiModelProperty(example = "123", required = true)
	@NotBlank
	private String currentPassword;
	
	@ApiModelProperty(example = "123", required = true)
	@NotBlank
	private String newPassword;
}
