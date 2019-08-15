/**
 * 
 */
package br.com.alura.Forum.repository;

import java.util.Optional;

import br.com.alura.Forum.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author geovan.goes
 *
 */
public interface UsuarioRepository  extends JpaRepository<Usuario, Long>
{
	Optional<Usuario> findByEmail(String email);
}
