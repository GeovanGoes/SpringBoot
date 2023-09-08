package com.goes.demoapikey.auth.apikey;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class APIKeyAuthentication extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6832583990824081316L;
	
	private final String apiKey;
	
	public APIKeyAuthentication(Collection<? extends GrantedAuthority> authorities, String apiKey) {
		super(authorities);
		this.apiKey = apiKey;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return apiKey;
	}

}
