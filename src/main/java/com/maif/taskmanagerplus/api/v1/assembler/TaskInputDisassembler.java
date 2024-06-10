package com.maif.taskmanagerplus.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maif.taskmanagerplus.api.v1.model.input.TaskInput;
import com.maif.taskmanagerplus.domain.model.Task;

@Component
public class TaskInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Task toDomainObject(TaskInput taskInput) {
		return modelMapper.map(taskInput, Task.class);
	}
	
	public void copyToDomainObject(TaskInput taskInput, Task task) {
		modelMapper.map(taskInput, task);
	}
	
}
