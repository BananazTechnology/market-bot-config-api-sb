package tech.bananaz.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.bananaz.spring.models.ListingEvent;

@Repository
public interface ListingEventRepository extends JpaRepository<ListingEvent, Long> {}
