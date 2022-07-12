package tech.bananaz.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.bananaz.spring.models.Listings;

public interface ListingsConfigRepository extends JpaRepository<Listings, Long> {
	
	Boolean existsById(long listingsId);
	
}
