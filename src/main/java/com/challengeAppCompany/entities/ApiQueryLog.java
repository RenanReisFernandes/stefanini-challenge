package com.challengeAppCompany.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "api_query_logs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiQueryLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "query_time", nullable = false)
	private LocalDateTime queryTime;
	
	@Column(name= "cep", nullable = false)
	private String cep;
	
	@Column(name= "response", columnDefinition = "TEXT")
	private String response;
	
	@Column(name = "status", nullable = false)
	private String status;

}
