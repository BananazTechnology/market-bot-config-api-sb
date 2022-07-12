package tech.bananaz.spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import tech.bananaz.spring.models.Listings;

public interface ListingsConfigPagingRepository extends PagingAndSortingRepository<Listings, Long> {
	
}
