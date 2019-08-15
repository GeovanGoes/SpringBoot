/**
 * 
 */
package br.com.alura.Forum.config.security;

import java.util.Optional;

import br.com.alura.Forum.model.Usuario;
import br.com.alura.Forum.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author geovan.goes
 *
 */
@Service
public class AutenticacaoService implements UserDetailsService 
{

	@Autowired
	UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		Optional<Usuario> usuario = repository.findByEmail(username);
		if (usuario.isPresent())
			return usuario.get();
		throw new UsernameNotFoundException("Dados inválidos.");
	}
}
