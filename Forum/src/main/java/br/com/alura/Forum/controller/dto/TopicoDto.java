/**
 * 
 */
package br.com.alura.Forum.controller.dto;

import br.com.alura.Forum.model.Topico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

/**
 * @author geovan.goes
 *
 */
public class TopicoDto {
	
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	
	/**
	 * 
	 */
	public TopicoDto(Topico topico) 
	{
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	} 
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}
	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param asList
	 * @return
	 */
	public static Page<TopicoDto> converter(Page<Topico> page) 
	{
		return page.map(TopicoDto::new);
	}
}
