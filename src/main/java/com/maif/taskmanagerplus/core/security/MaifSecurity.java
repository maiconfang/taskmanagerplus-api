package com.maif.taskmanagerplus.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class MaifSecurity {

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public boolean isAuthenticated() {
		return getAuthentication().isAuthenticated();
	}
	
	public Long getUsserrId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		return jwt.getClaim("usserr_id");
	}
	
	
	public boolean usserrAuthenticatedEqual(Long usserrId) {
		return getUsserrId() != null && usserrId != null
				&& getUsserrId().equals(usserrId);
	}
	
	public boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean hasWrittenScope() {
		return hasAuthority("SCOPE_WRITE");
	}
	
	public boolean hasReadingScope() {
		return hasAuthority("SCOPE_READ");
	}
	
	public boolean canConsultUsserrsGroupsPermissions() {
		return hasReadingScope() && hasAuthority("CONSULT_USSERRS_GROUPS_PERMISSIONS");
	}
	
	public boolean canEditUsserrsGroupsPermissions() {
		return hasWrittenScope() && hasAuthority("EDIT_USSERRS_GROUPS_PERMISSIONS");
	}
	
		
	public boolean canConsultProvinces() {
		return isAuthenticated() && hasReadingScope();
	}
	
	
	public boolean canConsultTasks() {
		return isAuthenticated() && hasReadingScope();
	}
	
			
}
