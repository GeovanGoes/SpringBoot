package com.goes.demoapikey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.goes.demoapikey.domain.usuario.Usuario;
import com.goes.demoapikey.domain.usuario.UsuarioAtualizacaoDTO;
import com.goes.demoapikey.domain.usuario.UsuarioDTO;
import com.goes.demoapikey.domain.usuario.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEnconder;
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> cadastrar (@RequestBody @Valid UsuarioDTO dto, UriComponentsBuilder uriBuilder) {
		System.out.println(dto);
		Usuario usuario = new Usuario(dto);
		usuario.setPassword(passwordEnconder.encode(dto.password()));
		log.info(usuario.getPassword());
		repository.save(usuario);
		var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDTO(usuario));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> detalhes(@PathVariable Long id){
		Usuario usuario = repository.getReferenceById(id);
		return ResponseEntity.ok(new UsuarioDTO(usuario));
	}
	
	@GetMapping
	public ResponseEntity<Page<UsuarioDTO>> listar(@PageableDefault(size = 10, sort = {"username"}) Pageable pageable) {
		return ResponseEntity.ok(repository.findAllByAtivoTrue(pageable).map(UsuarioDTO::new));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> atualizar(@RequestBody @Valid UsuarioAtualizacaoDTO dto) {
		Usuario usuario = repository.getReferenceById(dto.id());
		usuario.atualizarInformacoes(dto);
		return ResponseEntity.ok(new UsuarioDTO(usuario));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		repository.getReferenceById(id).inativarUsuario();
		return ResponseEntity.noContent().build();
	}
}
