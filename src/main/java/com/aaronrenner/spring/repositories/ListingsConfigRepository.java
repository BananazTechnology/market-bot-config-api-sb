package com.aaronrenner.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aaronrenner.spring.models.Listings;

public interface ListingsConfigRepository extends JpaRepository<Listings, Long> {
	
	Boolean existsById(long listingsId);
	
}
