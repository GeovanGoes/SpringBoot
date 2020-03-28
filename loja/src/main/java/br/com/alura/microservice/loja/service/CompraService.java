package br.com.alura.microservice.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.controller.dto.InfoFornecedorDTO;

@Service
public class CompraService {

	@Autowired
	private RestTemplate client;
	
	@Autowired
	private DiscoveryClient eurekaClient;
	
	public void realizaCompra(CompraDTO compraDto)
	{		
		ResponseEntity<InfoFornecedorDTO> response = client.exchange("http://fornecedor/info/" + compraDto.getEndereco().getEstado(), HttpMethod.GET, null, InfoFornecedorDTO.class);
		
		System.out.println(response.getBody().getEndereco());
		
		eurekaClient.getInstances("fornecedor").stream().forEach(fornecedor -> {
			System.out.println(fornecedor.getUri().toString() + fornecedor.getPort());
		});
		
	}
}
