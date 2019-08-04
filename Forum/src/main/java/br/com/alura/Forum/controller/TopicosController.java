/**
 * 
 */
package br.com.alura.Forum.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.Forum.controller.dto.TopicoDto;
import br.com.alura.Forum.controller.form.TopicoForm;
import br.com.alura.Forum.model.Topico;
import br.com.alura.Forum.repository.CursoRepository;
import br.com.alura.Forum.repository.TopicoRepository;

/**
 * @author geovan.goes
 *
 */

@RestController("/topicos")
public class TopicosController 
{

	@Autowired
	TopicoRepository topicoRepository;
	
	@Autowired
	CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto>lista(String nomeCurso)
	{
		if (nomeCurso == null)
			return TopicoDto.converter(topicoRepository.findAll());
		else
			return TopicoDto.converter(topicoRepository.findByCursoNome(nomeCurso));
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder)
	{
		Topico topico = topicoRepository.save(form.converter(cursoRepository));
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
}
