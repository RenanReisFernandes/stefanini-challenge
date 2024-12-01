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

	private static final String EXTERNAL_API_URL = "http://localhost:3001/ws/:cep/json";

	public Map<String, Object> getAddressByCep(String cep) {
		log.info("Iniciando consulta para o CEP: {}", cep);

		try {
			// CALLING EXTERNAL API
			ResponseEntity<Map> responseEntity = restTemplate.getForEntity(EXTERNAL_API_URL, Map.class, cep);

			Map<String, Object> responseBody = responseEntity.getBody();

			// CHECK IF THE RESPONSE CONTAINS ERRORS
			if (responseBody != null && responseBody.containsKey("erro")) {
				String errorMessage = "CEP inválido ou não encontrato: " + cep;
				log.warn(errorMessage);
				logService.saveLog(cep, errorMessage, "NOT_FOUND");

				throw new IllegalArgumentException(errorMessage);
			}

			log.info("Consulta concluída com sucesso: {}", responseBody);

			// SAVING THE LOG IN DATABASE
			logService.saveLog(cep, responseEntity.toString(), "SUCCESS");

			return responseBody;
		} catch (HttpClientErrorException.NotFound e) {
			String errorMessage = "Nenhum endereço encontrado para o CEP: " + cep;
			log.warn(errorMessage);
			logService.saveLog(cep, errorMessage, "NOT_FOUND");
			throw e;

		} catch (Exception e) {
			String errorMessage = "Falha ao consultar a API externa: " + e.getMessage();
			log.error(errorMessage);
			logService.saveLog(cep, errorMessage, "ERROR");
			throw e;
		}

	}

}
