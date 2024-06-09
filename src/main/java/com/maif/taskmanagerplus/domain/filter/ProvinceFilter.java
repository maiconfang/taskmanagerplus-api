package com.maif.taskmanagerplus.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProvinceFilter {

	@ApiModelProperty(example = "1", value = "ID of province of search")
	private Long provinceId;
	
	@ApiModelProperty(example = "Moncton", value = "Name of province of search")
	private String name;
	
	@ApiModelProperty(example = "NB", value = "FS of province of search")
	private String abbreviation;
	
	
}
