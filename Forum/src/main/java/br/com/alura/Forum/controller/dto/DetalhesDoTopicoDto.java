/**
 * 
 */
package br.com.alura.Forum.controller.dto;

import br.com.alura.Forum.model.Resposta;
import br.com.alura.Forum.model.StatusTopico;
import br.com.alura.Forum.model.Topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author geovan.goes
 *
 */
public class DetalhesDoTopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	private String nomeAutor;
	private StatusTopico status;
	private List<RespostaDto> respostas;
	
	/**
	 * 
	 */
	public DetalhesDoTopicoDto(Topico topico) 
	{
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
		this.nomeAutor = topico.getAutor().getNome();
		this.status = topico.getStatus();
		this.respostas = new ArrayList<>();
		this.respostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
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
	 * @return the nomeAutor
	 */
	public String getNomeAutor() {
		return nomeAutor;
	}

	/**
	 * @return the status
	 */
	public StatusTopico getStatus() {
		return status;
	}

	/**
	 * @return the respostas
	 */
	public List<RespostaDto> getRespostas() {
		return respostas;
	}
}
