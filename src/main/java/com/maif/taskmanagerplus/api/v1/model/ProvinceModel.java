package com.maif.taskmanagerplus.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "provinces")
@Setter
@Getter
public class ProvinceModel extends RepresentationModel<ProvinceModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "New Brunswick")
	private String name;
	
	@ApiModelProperty(example = "NB")
	private String abbreviation;
	
	
}
