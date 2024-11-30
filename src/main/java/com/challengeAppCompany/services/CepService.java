package com.challengeAppCompany.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CepService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApiQueryLogService logService;

	private static final String EXTERNAL_API_URL = "https://viacep.com.br/ws/{cep}/json";
	
	public Map<String, Object> getAddressByCep(String cep){
		log.info("Iniciando consulta para o CEP: {}", cep);
		
		try {
			//CALLING EXTERNAL API
			ResponseEntity<Map> responseEntity = restTemplate.getForEntity(EXTERNAL_API_URL, Map.class, cep);
			
			Map<String, Object> responseBody = responseEntity.getBody();
			log.info("Consulta concluída com sucesso: {}", responseBody);
			
			//SAVING THE LOG IN THE DATABASE
			logService.saveLog(cep, responseBody.toString(), "SUCCESS");
			
			return responseBody;
			
		}catch (HttpClientErrorException.NotFound ex) {
			log.warn("Nenhum endereço encontrado para o CEP: {}", cep);
			logService.saveLog(cep, "Nenhum endereço encontrado", "NOT_FOUND");
			throw ex;
			
		}catch(Exception e) {
			log.error("Erro ao consultar a API externa: {}", e.getMessage());
			logService.saveLog(cep, e.getMessage(), "ERROR");
			throw e;
		}
		
	}
}
