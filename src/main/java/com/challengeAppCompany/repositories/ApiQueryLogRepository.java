package com.challengeAppCompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challengeAppCompany.entities.ApiQueryLog;

public interface ApiQueryLogRepository extends JpaRepository<ApiQueryLog, Long> {

}
