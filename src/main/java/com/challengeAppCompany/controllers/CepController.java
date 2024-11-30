package com.challengeAppCompany.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.challengeAppCompany.services.CepService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cep")
@Slf4j
public class CepController {
	
	@Autowired
	private CepService cepService;

	@GetMapping("/{cep}")
	public ResponseEntity<Map<String, Object>> getCepDetails(@PathVariable String cep){
		log.info("Requisição para o CEP: {} recebida",cep);
		try {
			Map<String, Object> address = cepService.getAddressByCep(cep);
			return ResponseEntity.ok(address);
		}catch (HttpClientErrorException.NotFound e) {
			log.info("CEP {} não encontrado",cep);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}catch (Exception e) {
			log.info("Erro de servidor, CEP: {} não encontrado",cep);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
