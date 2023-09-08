package com.goes.demoapikey.auth.basic;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthenticationService {
	
	public Authentication getAuthentication(String username, String password) {
		
		BasicUser basicUser = getBasicUsers().stream().filter(bu -> bu.password().equals(password) && bu.username().equals(username)).findFirst().orElse(null);
		if (basicUser == null) {
			throw new BadCredentialsException("BASIC invalid.");
		} else {
			return new BasicAuthenticationToken(AuthorityUtils.NO_AUTHORITIES, username);
		}
		
	}
	
	private List<BasicUser> getBasicUsers() {
		return List.of(new BasicUser("user", "123456"));
	}

}
class BasicAuthenticationToken extends AbstractAuthenticationToken {

	private String username;

	public BasicAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String username) {
		super(authorities);
		this.username = username;
		setAuthenticated(true);
	}
	
	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return username;
	}
	
}