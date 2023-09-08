package com.goes.demoapikey.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> 
{

	Page<Usuario> findAllByAtivoTrue(Pageable pageable);

	Usuario findByUsernameAndAtivoTrue(String username);
	
	

}
