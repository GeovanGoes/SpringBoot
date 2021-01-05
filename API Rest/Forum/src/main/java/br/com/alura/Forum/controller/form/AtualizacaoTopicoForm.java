/**
 * 
 */
package br.com.alura.Forum.controller.form;

import br.com.alura.Forum.model.Topico;
import br.com.alura.Forum.repository.TopicoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * @author geovan.goes
 *
 */
public class AtualizacaoTopicoForm 
{

	@NotNull @NotEmpty @Length(min = 5)
	private String titulo;
	@NotNull @NotEmpty @Length(min = 10)
	private String mensagem;
	
	/**
	 * 
	 */
	public AtualizacaoTopicoForm() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public AtualizacaoTopicoForm(String titulo, String mensagem) 
	{
		super();
		this.titulo = titulo;
		this.mensagem = mensagem;
	}
	
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}
	/**
	 * @param mensagem the mensagem to set
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * @param id
	 * @param topicoRepository
	 */
	public Topico atualizar(Long id, TopicoRepository topicoRepository) 
	{
		Topico topico = topicoRepository.getOne(id);
		topico.setMensagem(getMensagem());
		topico.setTitulo(getTitulo());
		return topico;
	}
	
	
}
