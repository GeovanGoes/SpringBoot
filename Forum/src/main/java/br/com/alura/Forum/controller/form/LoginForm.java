/**
 * 
 */
package br.com.alura.Forum.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author geovan.goes
 *
 */
public class LoginForm 
{
	private String email;
	private String senha;
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}
	/**
	 * @return
	 */
	public UsernamePasswordAuthenticationToken converter() 
	{
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
}
