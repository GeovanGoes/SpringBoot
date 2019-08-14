/**
 * 
 */
package br.com.alura.Forum.config.validacao;

/**
 * @author geovan.goes
 *
 */
public class ErroDeFormularioDto 
{
	private String campo;
	private String erro;
	/**
	 * @param campo
	 * @param erro
	 */
	public ErroDeFormularioDto(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}
	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}
	/**
	 * @return the erro
	 */
	public String getErro() {
		return erro;
	}
}
