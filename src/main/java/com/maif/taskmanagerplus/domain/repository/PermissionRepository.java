package com.maif.taskmanagerplus.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.maif.taskmanagerplus.domain.model.Permission;

@Repository
public interface PermissionRepository extends CustomJpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
	
	List<Permission> findAll();

}
