package com.maif.taskmanagerplus.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maif.taskmanagerplus.domain.exception.EntityInUseException;
import com.maif.taskmanagerplus.domain.exception.TaskNotFoundException;
import com.maif.taskmanagerplus.domain.model.Task;
import com.maif.taskmanagerplus.domain.repository.TaskRepository;

@Service
public class RegisterTaskService {

	private static final String MSG_TASK_IN_USE_NAME 
	= "Task with the name \""+ "%s" +"\" can´t be removed, because it is in use";
	
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Transactional
	public Task save(Task task) {
		return taskRepository.save(task);
	}
	
	@Transactional
	public void delete(Long taskId, String nameTask) {
		try {
			taskRepository.deleteById(taskId);
			taskRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new TaskNotFoundException(taskId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
				String.format(MSG_TASK_IN_USE_NAME, nameTask));
		}
	}

	public Task findOrFail(Long taskId) {
		return taskRepository.findById(taskId)
			.orElseThrow(() -> new TaskNotFoundException(taskId));
	}
	
}
