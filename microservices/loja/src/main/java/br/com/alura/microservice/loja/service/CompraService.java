package br.com.alura.microservice.loja.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.client.TransportadorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.controller.dto.InfoEntregaDTO;
import br.com.alura.microservice.loja.controller.dto.InfoFornecedorDTO;
import br.com.alura.microservice.loja.controller.dto.InfoPedidoDTO;
import br.com.alura.microservice.loja.controller.dto.VoucherDTO;
import br.com.alura.microservice.loja.model.Compra;
import br.com.alura.microservice.loja.model.CompraState;
import br.com.alura.microservice.loja.repository.CompraRepository;

@Service
public class CompraService 
{
	
	private static final Logger _log = LoggerFactory.getLogger(CompraService.class);
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private TransportadorClient transportadorClient;
	
	//TODO: Implements
	public Compra cancelaCompra(Long id) {
		return null;
	}

	//TODO: Implements
	public Compra reprocessaCompra(Long id) {
		return null;
	}
	
	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compraDto)
	{		
		
		Compra compra = new Compra();
		compra.setEnderecoDestino(compraDto.getEndereco().toString());
		compra.setState(CompraState.RECEBIDO);
		compraRepository.save(compra);
		compraDto.setCompraId(compra.getId());
		
		_log.info("Indo buscar informacoes do fornecedor... estado: " + compraDto.getEndereco().getEstado());
		InfoFornecedorDTO infoPorEstado = this.fornecedorClient.getInfoPorEstado(compraDto.getEndereco().getEstado());
		_log.info("INformacoes obtidas.");
		
		_log.info(infoPorEstado.getEndereco());
		
		_log.info("Indo criar pedido no	 fornecedor...");
		InfoPedidoDTO pedidoDTO = fornecedorClient.realizaPedido(compraDto.getItens());
		compra.setPedidoId(pedidoDTO.getId());
		compra.setTempoDePreparo(pedidoDTO.getTempoDePreparo());
		compra.setState(CompraState.PEDIDO_REALIZADO);
		compraRepository.save(compra);
		_log.info("Pedido realizado no fornecedor.");
		
	
		InfoEntregaDTO entregaDTO = new InfoEntregaDTO(); 
		entregaDTO.setPedidoId(pedidoDTO.getId());
		entregaDTO.setDataParaEntrega(LocalDate.now().plusDays(pedidoDTO.getTempoDePreparo()));
		entregaDTO.setEnderecoOrigem(infoPorEstado.getEndereco());
		entregaDTO.setEnderecoDestino(compraDto.getEndereco().toString());
		
		VoucherDTO voucher = transportadorClient.reservaEntrega(entregaDTO);
		compra.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compra.setVoucher(voucher.getNumero());
		compra.setState(CompraState.RESERVA_ENTREGA_REALIZADA);
		compraRepository.save(compra);
		
		_log.info("Retornando dados para o usuario");
		
		return compra;
	}
	
	
	public Compra realizaCompraFallback(CompraDTO compraDto)
	{
		if(compraDto.getCompraId() != null) 
			return compraRepository.findById(compraDto.getCompraId()).get();
		
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
