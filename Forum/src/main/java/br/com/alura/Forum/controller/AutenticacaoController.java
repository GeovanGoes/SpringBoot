/**
 * 
 */
package br.com.alura.Forum.controller;

import br.com.alura.Forum.config.security.TokenService;
import br.com.alura.Forum.controller.dto.TokenDto;
import br.com.alura.Forum.controller.form.LoginForm;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author geovan.goes
 *
 */
@RestController
@RequestMapping("/auth")
public class AutenticacaoController 
{
	@Autowired
	AuthenticationManager authManager;

	@Autowired
	TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form)
	{
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		try 
		{	
			Authentication authentication = authManager.authenticate(dadosLogin);
			
			String token = tokenService.gerarToken(authentication);
			System.out.println(token);
			return ResponseEntity.ok().body(new TokenDto(token, "Bearer"));	
		} 
		catch (AuthenticationException e) 
		{
			return ResponseEntity.badRequest().build();
		}
	}
}
