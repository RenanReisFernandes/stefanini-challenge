package com.challengeAppCompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challengeAppCompany.entities.ApiQueryLog;

@Repository
public interface ApiQueryLogRepository extends JpaRepository<ApiQueryLog, Long> {

}
