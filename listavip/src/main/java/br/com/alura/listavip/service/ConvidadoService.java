package br.com.alura.listavip.service;

import br.com.alura.enviadorEmail.enviadorEmail.MailService;
import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.repository.ConvidadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvidadoService 
{

	@Autowired
	private ConvidadoRepository repository;
	
	public Iterable<Convidado> obterTodos()
	{
		Iterable<Convidado> convidados = repository.findAll();
		return convidados;
	}
	
	public void save(Convidado convidado)
	{
		repository.save(convidado);
		MailService mailService = new MailService();
		mailService.enviar(convidado.getNome(), convidado.getEmail());
	}
	
}
