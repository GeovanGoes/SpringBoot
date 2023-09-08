package com.goes.demoapikey.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
		Long id,
		@NotBlank
		String nome,
		@NotBlank
		String sobrenome,
		@NotBlank
		String username,
		@NotBlank
		String password
		) {
	
	
	public UsuarioDTO(Usuario usuario) {
		this(usuario.getId(), usuario.getNome(), usuario.getSobrenome(), usuario.getUsername(), null);
	}
	
}
