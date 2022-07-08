package com.aaronrenner.spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.aaronrenner.spring.models.Sales;

public interface SalesConfigPagingRepository extends PagingAndSortingRepository<Sales, Long> {
	
}
