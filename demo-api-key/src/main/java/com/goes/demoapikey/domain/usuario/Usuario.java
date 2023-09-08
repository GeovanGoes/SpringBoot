package com.goes.demoapikey.domain.usuario;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sobrenome;
	private String username;
	private String password;
	private boolean ativo;
	
	public Usuario(UsuarioDTO dto) {
		this.nome = dto.nome();
		this.sobrenome = dto.sobrenome();
		this.username = dto.username();
		this.password = dto.password();
		this.ativo = true;
	}

	public void atualizarInformacoes(@Valid UsuarioAtualizacaoDTO dto) {
		if (Objects.nonNull(dto.nome()))
			this.nome = dto.nome();
		if (Objects.nonNull(dto.sobrenome()))
			this.sobrenome = dto.sobrenome();
	}

	public void inativarUsuario() {
		this.ativo = false;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return ativo;
	}
	
	
}
