package com.maif.taskmanagerplus.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.maif.taskmanagerplus.domain.model.Usserr;

@Repository
public interface UsserrRepository extends CustomJpaRepository<Usserr, Long>, JpaSpecificationExecutor<Usserr> {

	Optional<Usserr> findByEmail(String email);
	List<Usserr> findAll();
	
}
