package com.maif.taskmanagerplus.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.maif.taskmanagerplus.domain.model.Province;

@Repository
public interface ProvinceRepository  extends CustomJpaRepository<Province, Long>, JpaSpecificationExecutor<Province> {
	
	List<Province> findAll();

}
