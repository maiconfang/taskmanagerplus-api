package com.maif.taskmanagerplus.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.maif.taskmanagerplus.domain.model.State;

@Repository
public interface StateRepository  extends CustomJpaRepository<State, Long>, JpaSpecificationExecutor<State> {
	
	List<State> findAll();

}
