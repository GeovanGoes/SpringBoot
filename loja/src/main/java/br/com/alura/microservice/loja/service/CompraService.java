package br.com.alura.microservice.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.controller.dto.InfoFornecedorDTO;
import br.com.alura.microservice.loja.controller.dto.InfoPedidoDTO;
import br.com.alura.microservice.loja.model.Compra;

@Service
public class CompraService 
{
	@Autowired
	private FornecedorClient fornecedorClient;
		
	public Compra realizaCompra(CompraDTO compraDto)
	{		
		InfoFornecedorDTO infoPorEstado = this.fornecedorClient.getInfoPorEstado(compraDto.getEndereco().getEstado());
		System.out.println(infoPorEstado.getEndereco());
		
		
		InfoPedidoDTO pedidoDTO = fornecedorClient.realizaPedido(compraDto.getItens());
		
		System.out.println(pedidoDTO.getTempoDePreparo());
		Compra compra = new Compra();
		
		compra.setPedidoId(pedidoDTO.getId());
		compra.setTempoDePreparo(pedidoDTO.getTempoDePreparo());
		compra.setEnderecoDestino(compraDto.getEndereco().toString());
		
		return compra;
	}
}
