package com.maif.taskmanagerplus.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.maif.taskmanagerplus.api.v1.MaifLinks;
import com.maif.taskmanagerplus.api.v1.controller.TaskController;
import com.maif.taskmanagerplus.api.v1.model.TaskModel;
import com.maif.taskmanagerplus.core.security.MaifSecurity;
import com.maif.taskmanagerplus.domain.model.Task;

@Component
public class TaskModelAssembler 
		extends RepresentationModelAssemblerSupport<Task, TaskModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MaifLinks maifLinks;
	
	@Autowired
	private MaifSecurity maifSecurity;
	
	public TaskModelAssembler() {
		super(TaskController.class, TaskModel.class);
	}
	
	@Override
	public TaskModel toModel(Task task) {
		TaskModel taskModel = createModelWithId(task.getId(), task);
		modelMapper.map(task, taskModel);
		
		if (maifSecurity.canConsultTasks()) {
			taskModel.add(maifLinks.linkToTask("tasks"));
		}
		
		return taskModel;
	}
	
	@Override
	public CollectionModel<TaskModel> toCollectionModel(Iterable<? extends Task> entities) {
		CollectionModel<TaskModel> collectionModel = super.toCollectionModel(entities);
		
		if (maifSecurity.canConsultTasks()) {
			collectionModel.add(maifLinks.linkToTasks());
		}
		
		return collectionModel;
	}
	
}
