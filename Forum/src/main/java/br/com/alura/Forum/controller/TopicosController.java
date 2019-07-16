/**
 * 
 */
package br.com.alura.Forum.controller;

import br.com.alura.Forum.controller.dto.TopicoDto;
import br.com.alura.Forum.model.Curso;
import br.com.alura.Forum.model.Topico;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author geovan.goes
 *
 */

@RestController
public class TopicosController {

	@RequestMapping("/topicos")
	public List<TopicoDto>lista()
	{
		Topico topico = new Topico("Dúvida", "Olá, deu merda aqui hein...", new Curso("Spring", "Programação"));
		return TopicoDto.converter(Arrays.asList(topico, topico, topico));
	}
}
