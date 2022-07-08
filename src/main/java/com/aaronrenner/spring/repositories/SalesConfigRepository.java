package com.aaronrenner.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aaronrenner.spring.models.Sales;

public interface SalesConfigRepository extends JpaRepository<Sales, Long> {
	
	Boolean existsById(long salesId);
	
}
