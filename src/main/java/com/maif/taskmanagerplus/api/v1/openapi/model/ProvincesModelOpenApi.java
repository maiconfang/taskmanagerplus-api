package com.maif.taskmanagerplus.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.maif.taskmanagerplus.api.v1.model.ProvinceModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ProvincesModel")
@Data
public class ProvincesModelOpenApi {

	private ProvincesEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("ProvincesEmbeddedModel")
	@Data
	public class ProvincesEmbeddedModelOpenApi {
		
		private List<ProvinceModel> provinces;
		
	}
	
}
