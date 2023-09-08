package com.goes.demoapikey.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record UsuarioAtualizacaoDTO(@NotNull Long id, String nome, String sobrenome) {
	
}
