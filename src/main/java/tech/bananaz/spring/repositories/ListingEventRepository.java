package tech.bananaz.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tech.bananaz.spring.models.ListingEvent;

@Repository
public interface ListingEventRepository extends JpaRepository<ListingEvent, Long> {
	
	@Query("SELECT e FROM ListingEvent e ORDER BY created_date DESC")
	List<ListingEvent> findAllOrderByCreatedDateDesc();
	
}
