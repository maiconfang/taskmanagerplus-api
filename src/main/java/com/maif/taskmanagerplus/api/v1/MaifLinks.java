package com.maif.taskmanagerplus.api.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.stereotype.Component;

import com.maif.taskmanagerplus.api.v1.controller.GroupController;
import com.maif.taskmanagerplus.api.v1.controller.GroupPermissionController;
import com.maif.taskmanagerplus.api.v1.controller.PermissionController;
import com.maif.taskmanagerplus.api.v1.controller.ProvinceController;
import com.maif.taskmanagerplus.api.v1.controller.UserGroupController;
import com.maif.taskmanagerplus.api.v1.controller.UsserrController;

@Component
public class MaifLinks {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projection", VariableType.REQUEST_PARAM));
	
	
	public Link linkToUsserr(Long usserrId, String rel) {
		return linkTo(methodOn(UsserrController.class)
				.find(usserrId)).withRel(rel);
	}
	
	public Link linkToUsserr(Long usserrId) {
		return linkToUsserr(usserrId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToUsserrs(String rel) {
		return linkTo(UsserrController.class).withRel(rel);
	}
	
	public Link linkToUsserrs() {
		return linkToUsserrs(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToUsserrGropuAssociation(Long usserrId, String rel) {
		return linkTo(methodOn(UserGroupController.class)
				.associate(usserrId, null)).withRel(rel);
	}
	
	public Link linkToUsserrGroupDisfellowshipping(Long usserrId, Long groupId, String rel) {
		return linkTo(methodOn(UserGroupController.class)
				.disassociate(usserrId, groupId)).withRel(rel);
	}
	
	public Link linkToGroupsUsserr(Long usserrId, String rel) {
		return linkTo(methodOn(UserGroupController.class)
				.list(usserrId)).withRel(rel);
	}
	
	public Link linkToGroupsUsserrs(Long usserrId) {
		return linkToGroupsUsserr(usserrId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGroups(String rel) {
		return linkTo(GroupController.class).withRel(rel);
	}
	
	public Link linkToGroups() {
		return linkToGroups(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToPermissions(String rel) {
		return linkTo(PermissionController.class).withRel(rel);
	}
	
	public Link linkToPermissions() {
		return linkToPermissions(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGroupPermissions(Long groupId, String rel) {
		return linkTo(methodOn(GroupPermissionController.class)
				.list(groupId)).withRel(rel);
	}
	
	public Link linkToGrupoPermissions(Long groupId) {
		return linkToGroupPermissions(groupId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGroupPermissionAssociation(Long groupId, String rel) {
		return linkTo(methodOn(GroupPermissionController.class)
				.associate(groupId, null)).withRel(rel);
	}
	
	public Link linkToGropuPermissionDisassociate(Long groupId, Long permissionId, String rel) {
		return linkTo(methodOn(GroupPermissionController.class)
				.disassociate(groupId, permissionId)).withRel(rel);
	}
	
	public Link linkToProvince(Long provinceId, String rel) {
		return linkTo(methodOn(ProvinceController.class)
				.find(provinceId)).withRel(rel);
	}
	
	public Link linkToProvince(Long provinceId) {
		return linkToProvince(provinceId, IanaLinkRelations.SELF.value());
	}

	
	public Link linkToProvince(String rel) {
		return linkTo(ProvinceController.class).withRel(rel);
	}
	
	
	public Link linkToProvinces() {
		return linkToProvince(IanaLinkRelations.SELF.value());
	}
	
	
	
}
