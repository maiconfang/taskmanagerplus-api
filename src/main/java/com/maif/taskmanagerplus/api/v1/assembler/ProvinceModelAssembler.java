package com.maif.taskmanagerplus.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.maif.taskmanagerplus.api.v1.MaifLinks;
import com.maif.taskmanagerplus.api.v1.controller.ProvinceController;
import com.maif.taskmanagerplus.api.v1.model.ProvinceModel;
import com.maif.taskmanagerplus.core.security.MaifSecurity;
import com.maif.taskmanagerplus.domain.model.Province;

@Component
public class ProvinceModelAssembler 
		extends RepresentationModelAssemblerSupport<Province, ProvinceModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MaifLinks maifLinks;
	
	@Autowired
	private MaifSecurity maifSecurity;
	
	public ProvinceModelAssembler() {
		super(ProvinceController.class, ProvinceModel.class);
	}
	
	@Override
	public ProvinceModel toModel(Province province) {
		ProvinceModel provinceModel = createModelWithId(province.getId(), province);
		modelMapper.map(province, provinceModel);
		
		if (maifSecurity.canConsultProvinces()) {
			provinceModel.add(maifLinks.linkToProvince("provinces"));
		}
		
		return provinceModel;
	}
	
	@Override
	public CollectionModel<ProvinceModel> toCollectionModel(Iterable<? extends Province> entities) {
		CollectionModel<ProvinceModel> collectionModel = super.toCollectionModel(entities);
		
		if (maifSecurity.canConsultProvinces()) {
			collectionModel.add(maifLinks.linkToProvinces());
		}
		
		return collectionModel;
	}
	
}
