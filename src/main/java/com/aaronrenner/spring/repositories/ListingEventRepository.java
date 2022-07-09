package com.aaronrenner.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aaronrenner.spring.models.ListingEvent;

@Repository
public interface ListingEventRepository extends JpaRepository<ListingEvent, Long> {}
