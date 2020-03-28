package br.com.alura.microservice.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.controller.dto.InfoFornecedorDTO;

@Service
public class CompraService 
{
	@Autowired
	private FornecedorClient fornecedorClient;
		
	public void realizaCompra(CompraDTO compraDto)
	{		
		InfoFornecedorDTO infoPorEstado = this.fornecedorClient.getInfoPorEstado(compraDto.getEndereco().getEstado());
		System.out.println(infoPorEstado.getEndereco());
		
	}
}
