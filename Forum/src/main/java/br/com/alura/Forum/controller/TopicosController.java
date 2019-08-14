/**
 * 
 */
package br.com.alura.Forum.controller;

import br.com.alura.Forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.Forum.controller.dto.TopicoDto;
import br.com.alura.Forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.Forum.controller.form.TopicoForm;
import br.com.alura.Forum.model.Topico;
import br.com.alura.Forum.repository.CursoRepository;
import br.com.alura.Forum.repository.TopicoRepository;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author geovan.goes
 *
 */

@RestController
@RequestMapping("/topicos")
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
	
	@Transactional
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder)
	{
		Topico topico = topicoRepository.save(form.converter(cursoRepository));
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public DetalhesDoTopicoDto detalhar(@PathVariable Long id)
	{
		Topico topico = topicoRepository.getOne(id);
		return new DetalhesDoTopicoDto(topico);
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form)
	{
		Topico topico = form.atualizar(id, topicoRepository);
		return ResponseEntity.ok(new TopicoDto(topico));
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id)
	{
		topicoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
