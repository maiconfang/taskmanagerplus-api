package com.maif.taskmanagerplus.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	
	public @interface Provinces {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_PROVINCES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit { }

		@PreAuthorize("@maifSecurity.canConsultProvinces()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanConsult { }
		
	}
	
	
	public @interface UsersGroupPermission {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and @maifSecurity.usserrAuthenticatedEqual(#usserrId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEditPropertyPassword { }
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDIT_USSERRS_GROUPS_PERMISSIONS') or "
				+ "@maifSecurity.usserrAuthenticatedEqual(#usserrId))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEditUser { }

		@PreAuthorize("@maifSecurity.canEditUsserrsGroupsPermissions()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit { }
		

		@PreAuthorize("@maifSecurity.canConsultUsserrsGroupsPermissions()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanConsult { }
		
	}
	
		
	
}
