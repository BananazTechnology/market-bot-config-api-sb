package tech.bananaz.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.bananaz.spring.models.Sales;

public interface SalesConfigRepository extends JpaRepository<Sales, Long> {
	
	Boolean existsById(long salesId);
	
}
