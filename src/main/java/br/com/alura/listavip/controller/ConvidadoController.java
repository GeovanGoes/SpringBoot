package br.com.alura.listavip.controller;

import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.service.ConvidadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ConvidadoController 
{
	@Autowired
	private ConvidadoService service;
	
	@RequestMapping("/")
	public String index()
	{
		return "index";
	}
	
	@RequestMapping("listaconvidados")
	public String listaConvidados(Model model)
	{	
		model.addAttribute("convidados" , service.obterTodos());
		return "listaConvidados";
	}
	
	@RequestMapping(value="salvar", method = RequestMethod.POST)
	public String salvar(Convidado convidado, Model model)
	{
		service.save(convidado);
		return listaConvidados(model);
	}
}