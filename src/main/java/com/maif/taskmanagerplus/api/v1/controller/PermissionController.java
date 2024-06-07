package com.maif.taskmanagerplus.api.v1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maif.taskmanagerplus.api.v1.assembler.PermissionModelAssembler;
import com.maif.taskmanagerplus.api.v1.model.PermissionModel;
import com.maif.taskmanagerplus.api.v1.openapi.controller.PermissionControllerOpenApi;
import com.maif.taskmanagerplus.core.data.PageWrapper;
import com.maif.taskmanagerplus.core.data.PageableTranslator;
import com.maif.taskmanagerplus.core.security.CheckSecurity;
import com.maif.taskmanagerplus.domain.filter.PermissionFilter;
import com.maif.taskmanagerplus.domain.model.Permission;
import com.maif.taskmanagerplus.domain.repository.PermissionRepository;
import com.maif.taskmanagerplus.infrastructure.repository.spec.PermissionSpecs;

@RestController
@RequestMapping(path = "/v1/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController implements PermissionControllerOpenApi {

	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private PermissionModelAssembler permissionModelAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Permission> pagedResourcesAssembler;
	
	@CheckSecurity.UsersGroupPermission.CanConsult
	@Override
	@GetMapping
	public PagedModel<PermissionModel> list(PermissionFilter filter, Pageable pageable) {
		
		Pageable pageableTranslate = translatePageable(pageable);
		Page<Permission> permissionsPage = null;
		
		if(filter.getDescription()!=null ) {
			permissionsPage = permissionRepository.findAll(PermissionSpecs.withDescription(filter), pageableTranslate);
		}
		else
		permissionsPage = permissionRepository.findAll(pageable);
		
		permissionsPage = new PageWrapper<>(permissionsPage, pageable);
		
		return pagedResourcesAssembler.toModel(permissionsPage, permissionModelAssembler);
	}
	
	private Pageable translatePageable(Pageable apiPageable) {
		var mapping = Map.of(
				"id", "code",
				"name", "name",
				"description", "description"
			);
		
		return PageableTranslator.translate(apiPageable, mapping);
	}
	
}
