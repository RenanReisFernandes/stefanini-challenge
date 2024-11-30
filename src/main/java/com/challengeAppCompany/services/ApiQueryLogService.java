package com.challengeAppCompany.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challengeAppCompany.entities.ApiQueryLog;
import com.challengeAppCompany.repositories.ApiQueryLogRepository;


@Service
public class ApiQueryLogService {
	
	@Autowired
	private ApiQueryLogRepository repository;
	
	public void saveLog(String cep, String response, String status) {
	
		ApiQueryLog log = new ApiQueryLog();
		log.setQueryTime(LocalDateTime.now());
		log.setCep(cep);
		log.setResponse(response);
		log.setStatus(status);
		
		repository.save(log);
	}

}
