package tech.bananaz.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.bananaz.spring.discord.DiscordBot;
import tech.bananaz.spring.exceptions.ResourceNotFoundException;
import tech.bananaz.spring.models.Sales;
import tech.bananaz.spring.repositories.SalesConfigPagingRepository;
import tech.bananaz.spring.repositories.SalesConfigRepository;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class SalesService {
	
	// Assets for Listing Config
	@Autowired
	SalesConfigRepository salesRepository;
	@Autowired
	SalesConfigPagingRepository salePagingRepository;
	private final String salesNotFoundException = "Sales with the value %s was not found";
	
	@Transactional
	public ResponseEntity<Sales> createSales(HttpServletRequest request, Sales sale) {
		// Set defaults
		if(isNull(sale.getActive())) 		  	 sale.setActive(true);
		if(isNull(sale.getAutoRarity())) 	  	 sale.setAutoRarity(false);
		if(isNull(sale.getContractIsSlug()))   	 sale.setContractIsSlug(false);
		if(isNull(sale.getExcludeDiscord()))   	 sale.setExcludeDiscord(false);
		if(isNull(sale.getExcludeTwitter()))   	 sale.setExcludeTwitter(false);
		if(isNull(sale.getExcludeLooksrare())) 	 sale.setExcludeLooksrare(false);
		if(isNull(sale.getExcludeOpensea()))   	 sale.setExcludeOpensea(false);
		if(isNull(sale.getShowBundles())) 	  	 sale.setShowBundles(true);
		if(isNull(sale.getBurnWatcher())) 	  	 sale.setBurnWatcher(false);
		if(isNull(sale.getMintWatcher())) 	  	 sale.setMintWatcher(false);
		// Validate Access
		if(nonNull(sale.getDiscordToken()) && nonNull(sale.getDiscordChannelId()))
			new DiscordBot(sale.getDiscordToken(), sale.getDiscordChannelId());
		// Run function
		Sales newConf = salesRepository.save(sale);
		// Build response
		return ResponseEntity
				.created(URI.create(request.getRequestURL()+"/"+newConf.getId().toString()))
				.body(newConf);
	}
	
	@Transactional
	public ResponseEntity<?> readAllSales(int page, int limit, Boolean showAll) {
		// If asking for the older way of showing all
		if(showAll) return ResponseEntity.ok(salesRepository.findAll());
		// Everything else paging
		Pageable where = PageRequest.of(page, limit);
		return ResponseEntity.ok(salePagingRepository.findAll(where));
	}
	
	@Transactional
	public ResponseEntity<Sales> readSales(long salesId) {
		// Ensure exists or throw error
		checkSalesExists(salesId);
		// Build response
		return ResponseEntity.ok(
				salesRepository.findById(salesId).get());
	}
	
	@Transactional
	public ResponseEntity<String> updateSales(long salesId, Sales sale) {
		// Ensure exists or throw error
		checkSalesExists(salesId);
		// Get existing
		Sales existingConf = salesRepository.findById(salesId).get();
		// Update provided
		if(nonNull(sale.getContractAddress())) 	 	    existingConf.setContractAddress(sale.getContractAddress());
		if(nonNull(sale.getInterval())) 		  	    existingConf.setInterval(sale.getInterval());
		if(nonNull(sale.getDiscordMessageColor())) 	    existingConf.setDiscordMessageColor(sale.getDiscordMessageColor());
		if(nonNull(sale.getDiscordServerId())) 	  	    existingConf.setDiscordServerId(sale.getDiscordServerId());
		if(nonNull(sale.getDiscordChannelId())) 	    existingConf.setDiscordChannelId(sale.getDiscordChannelId());
		if(nonNull(sale.getDiscordToken())) 	  	  	existingConf.setDiscordToken(sale.getDiscordToken());
		if(nonNull(sale.getTwitterApiKey())) 	  	   	existingConf.setTwitterApiKey(sale.getTwitterApiKey());
		if(nonNull(sale.getTwitterApiKeySecret())) 	   	existingConf.setTwitterApiKeySecret(sale.getTwitterApiKeySecret());
		if(nonNull(sale.getTwitterAccessToken()))  	   	existingConf.setTwitterAccessToken(sale.getTwitterAccessToken());
		if(nonNull(sale.getTwitterAccessTokenSecret())) existingConf.setTwitterAccessTokenSecret(sale.getTwitterAccessTokenSecret());
		if(nonNull(sale.getShowBundles())) 	  		   	existingConf.setShowBundles(sale.getShowBundles());
		if(nonNull(sale.getExcludeDiscord()))   		existingConf.setExcludeDiscord(sale.getExcludeDiscord());
		if(nonNull(sale.getExcludeTwitter()))   		existingConf.setExcludeTwitter(sale.getExcludeTwitter());
		if(nonNull(sale.getExcludeLooksrare())) 		existingConf.setExcludeLooksrare(sale.getExcludeLooksrare());
		if(nonNull(sale.getExcludeOpensea()))   		existingConf.setExcludeOpensea(sale.getExcludeOpensea());
		if(nonNull(sale.getBurnWatcher())) 	  			existingConf.setBurnWatcher(sale.getBurnWatcher());
		if(nonNull(sale.getMintWatcher())) 	  			existingConf.setMintWatcher(sale.getMintWatcher());
		if(nonNull(sale.getLastOpenseaId()))			existingConf.setLastOpenseaId(sale.getLastOpenseaId());
		if(nonNull(sale.getLastLooksId()))			    existingConf.setLastLooksId(sale.getLastLooksId());
		if(nonNull(sale.getActive())) 		   		    existingConf.setActive(sale.getActive());
		// Validate Access
		if(nonNull(existingConf.getDiscordToken()) && nonNull(existingConf.getDiscordChannelId()))
			new DiscordBot(existingConf.getDiscordToken(), existingConf.getDiscordChannelId());
		// Save
		salesRepository.save(existingConf);
		// Build response
		return ResponseEntity.noContent().build();
	}
	
	@Transactional
	public ResponseEntity<String> deleteSales(long salesId) {
		// Ensure exists or throw error
		checkSalesExists(salesId);
		// Delete
		salesRepository.deleteById(salesId);
		// Build response
		return ResponseEntity.noContent().build();
	}
	
	/*
	 * Helper function checks for existence in the database
	 */
	private void checkSalesExists(long salesId) {
		// Check existence
		if(!salesRepository.existsById(salesId))
			throw new ResourceNotFoundException(String.format(salesNotFoundException, salesId));
	}

}
