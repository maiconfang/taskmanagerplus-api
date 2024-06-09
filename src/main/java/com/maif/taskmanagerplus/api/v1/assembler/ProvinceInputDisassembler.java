package com.maif.taskmanagerplus.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maif.taskmanagerplus.api.v1.model.input.ProvinceInput;
import com.maif.taskmanagerplus.domain.model.Province;

@Component
public class ProvinceInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Province toDomainObject(ProvinceInput provinceInput) {
		return modelMapper.map(provinceInput, Province.class);
	}
	
	public void copyToDomainObject(ProvinceInput provinceInput, Province province) {
		modelMapper.map(provinceInput, province);
	}
	
}
