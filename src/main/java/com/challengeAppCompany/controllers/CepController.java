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


@RestController
@RequestMapping("/api/cep")
public class CepController {
	
	@Autowired
	private CepService cepService;

	@GetMapping("/{cep}")
	public ResponseEntity<Map<String, Object>> getCepDetails(@PathVariable String cep){
	
		try {
			Map<String, Object> address = cepService.getAddressByCep(cep);
			return ResponseEntity.ok(address);
		}catch (HttpClientErrorException.NotFound e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
