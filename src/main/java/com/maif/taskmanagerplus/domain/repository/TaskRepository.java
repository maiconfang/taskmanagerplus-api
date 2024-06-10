package com.maif.taskmanagerplus.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.maif.taskmanagerplus.domain.model.Task;

@Repository
public interface TaskRepository  extends CustomJpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
	
	List<Task> findAll();

}
