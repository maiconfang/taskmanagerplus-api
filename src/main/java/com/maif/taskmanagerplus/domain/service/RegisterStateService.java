package com.maif.taskmanagerplus.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maif.taskmanagerplus.domain.exception.EntityInUseException;
import com.maif.taskmanagerplus.domain.exception.StateNotFoundException;
import com.maif.taskmanagerplus.domain.model.State;
import com.maif.taskmanagerplus.domain.repository.StateRepository;

@Service
public class RegisterStateService {

	private static final String MSG_STATE_IN_USE_NAME 
	= "State with the name \""+ "%s" +"\" can´t be removed, because it is in use";
	
	
	@Autowired
	private StateRepository stateRepository;
	
	@Transactional
	public State save(State state) {
		return stateRepository.save(state);
	}
	
	@Transactional
	public void delete(Long stateId, String nameState) {
		try {
			stateRepository.deleteById(stateId);
			stateRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new StateNotFoundException(stateId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
				String.format(MSG_STATE_IN_USE_NAME, nameState));
		}
	}

	public State findOrFail(Long stateId) {
		return stateRepository.findById(stateId)
			.orElseThrow(() -> new StateNotFoundException(stateId));
	}
	
}
