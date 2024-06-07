package com.maif.taskmanagerplus.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maif.taskmanagerplus.api.v1.model.input.GroupppInput;
import com.maif.taskmanagerplus.domain.model.Grouppp;

@Component
public class GroupppInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Grouppp toDomainObject(GroupppInput groupppInput) {
		return modelMapper.map(groupppInput, Grouppp.class);
	}
	
	public void copyToDomainObject(GroupppInput groupInput, Grouppp group) {
		modelMapper.map(groupInput, group);
	}
	
}
