package tech.bananaz.spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import tech.bananaz.spring.models.Sales;

public interface SalesConfigPagingRepository extends PagingAndSortingRepository<Sales, Long> {
	
}
