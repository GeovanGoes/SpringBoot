/**
 * 
 */
package br.com.alura.Forum.controller.dto;

/**
 * @author geovan.goes
 *
 */
public class TokenDto 
{
	private String token;
	private String string;

	/**
	 * @param token
	 * @param string
	 */
	public TokenDto(String token, String string) 
	{
		this.token = token;
		this.string = string;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @return the string
	 */
	public String getString() {
		return string;
	}
}
