package br.com.alura.microservice.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.controller.dto.InfoFornecedorDTO;
import br.com.alura.microservice.loja.controller.dto.InfoPedidoDTO;
import br.com.alura.microservice.loja.model.Compra;
import br.com.alura.microservice.loja.repository.CompraRepository;

@Service
public class CompraService 
{
	
	private static final Logger _log = LoggerFactory.getLogger(CompraService.class);
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private FornecedorClient fornecedorClient;
		
	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compraDto)
	{		
		_log.info("Indo buscar informacoes do fornecedor... estado: " + compraDto.getEndereco().getEstado());
		InfoFornecedorDTO infoPorEstado = this.fornecedorClient.getInfoPorEstado(compraDto.getEndereco().getEstado());
		_log.info("INformacoes obtidas.");
		
		_log.info(infoPorEstado.getEndereco());
		
		_log.info("Indo criar pedido no	 fornecedor...");
		InfoPedidoDTO pedidoDTO = fornecedorClient.realizaPedido(compraDto.getItens());
		_log.info("Pedido realizado no fornecedor.");
	
		Compra compra = new Compra();
		
		compra.setPedidoId(pedidoDTO.getId());
		compra.setTempoDePreparo(pedidoDTO.getTempoDePreparo());
		compra.setEnderecoDestino(compraDto.getEndereco().toString());
				
		_log.info("Retornando dados para o usuario");
		
		compraRepository.save(compra);
		
		return compra;
	}
	
	
	public Compra realizaCompraFallback(CompraDTO compraDto)
	{
		Compra fallback = new Compra();
		fallback.setEnderecoDestino(compraDto.getEndereco().toString());
		return fallback;
	}
	
	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id)
	{
		return compraRepository.findById(id).orElse(new Compra());
	}
}
