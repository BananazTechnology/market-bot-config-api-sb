package tech.bananaz.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import antlr.debug.Event;
import tech.bananaz.models.Listing;
import tech.bananaz.models.Sale;
import tech.bananaz.repositories.EventPagingRepository;
import tech.bananaz.repositories.ListingConfigPagingRepository;
import tech.bananaz.repositories.SaleConfigPagingRepository;
import tech.bananaz.spring.Application;

@SpringBootApplication
@ComponentScan({"tech.bananaz.*"})
@EnableJpaRepositories(basePackageClasses = {
	EventPagingRepository.class, 
	ListingConfigPagingRepository.class, 
	SaleConfigPagingRepository.class})
@EntityScan(basePackageClasses = {
	Event.class, 
	Listing.class, 
	Sale.class})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
