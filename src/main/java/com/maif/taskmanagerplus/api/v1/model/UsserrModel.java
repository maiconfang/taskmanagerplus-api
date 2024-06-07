package com.maif.taskmanagerplus.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usserrs")
@Setter
@Getter
public class UsserrModel extends RepresentationModel<UsserrModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Maicon Alexander")
	private String name;
	
	@ApiModelProperty(example = "maicon.alexander@taskmanagerplus.com.br")
	private String email;
	
}
