package tech.bananaz.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.bananaz.spring.discord.DiscordBot;
import tech.bananaz.spring.exceptions.ResourceNotFoundException;
import tech.bananaz.spring.models.Listings;
import tech.bananaz.spring.repositories.ListingsConfigPagingRepository;
import tech.bananaz.spring.repositories.ListingsConfigRepository;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class ListingsService {
	
	// Assets for Listing Config
	@Autowired
	ListingsConfigRepository listingsRepository;
	@Autowired
	ListingsConfigPagingRepository listPagingRepository;
	
	private final String listingsNotFoundException = "Listings with the value %s was not found";
	
	@Transactional
	public ResponseEntity<Listings> createListings(HttpServletRequest request, Listings listing) {
		// Set defaults
		if(isNull(listing.getActive())) 		  	 listing.setActive(true);
		if(isNull(listing.getAutoRarity())) 	  	 listing.setAutoRarity(false);
		if(isNull(listing.getContractIsSlug()))   	 listing.setContractIsSlug(false);
		if(isNull(listing.getExcludeDiscord()))   	 listing.setExcludeDiscord(false);
		if(isNull(listing.getExcludeTwitter()))   	 listing.setExcludeTwitter(false);
		if(isNull(listing.getExcludeLooksrare())) 	 listing.setExcludeLooksrare(false);
		if(isNull(listing.getExcludeOpensea()))   	 listing.setExcludeOpensea(false);
		if(isNull(listing.getShowBundles())) 	  	 listing.setShowBundles(true);
		if(isNull(listing.getSolanaOnOpensea())) 	 listing.setSolanaOnOpensea(false);
		// Validate Access
		if(nonNull(listing.getDiscordToken()) && nonNull(listing.getDiscordChannelId()))
			new DiscordBot(listing.getDiscordToken(), listing.getDiscordChannelId());
		// Run function
		Listings newConf = listingsRepository.save(listing);
		// Build response
		return ResponseEntity
				.created(URI.create(request.getRequestURL()+"/"+newConf.getId().toString()))
				.body(newConf);
	}
	
	@Transactional
	public ResponseEntity<?> readAllListings(int page, int limit, Boolean showAll) {
		// If asking for the older way of showing all
		if(showAll) return ResponseEntity.ok(listingsRepository.findAll());
		// Everything else paging
		Pageable where = PageRequest.of(page, limit);
		return ResponseEntity.ok(listPagingRepository.findAll(where));
	}
	
	@Transactional
	public ResponseEntity<Listings> readListings(long listingsId) {
		// Ensure exists or throw error
		checkListingsExists(listingsId);
		// Build response
		return ResponseEntity.ok(
				listingsRepository.findById(listingsId).get());
	}
	
	@Transactional
	public ResponseEntity<String> updateListings(long listingsId, Listings listing) {
		// Ensure exists or throw error
		checkListingsExists(listingsId);
		// Get existing
		Listings existingConf = listingsRepository.findById(listingsId).get();
		// Update provided
		if(nonNull(listing.getContractAddress())) 	 	   existingConf.setContractAddress(listing.getContractAddress());
		if(nonNull(listing.getInterval())) 		  	  	   existingConf.setInterval(listing.getInterval());
		if(nonNull(listing.getDiscordMessageColor())) 	   existingConf.setDiscordMessageColor(listing.getDiscordMessageColor());
		if(nonNull(listing.getDiscordServerId())) 	  	   existingConf.setDiscordServerId(listing.getDiscordServerId());
		if(nonNull(listing.getDiscordChannelId())) 	  	   existingConf.setDiscordChannelId(listing.getDiscordChannelId());
		if(nonNull(listing.getDiscordToken())) 	  	  	   existingConf.setDiscordToken(listing.getDiscordToken());
		if(nonNull(listing.getTwitterApiKey())) 	  	   existingConf.setTwitterApiKey(listing.getTwitterApiKey());
		if(nonNull(listing.getTwitterApiKeySecret())) 	   existingConf.setTwitterApiKeySecret(listing.getTwitterApiKeySecret());
		if(nonNull(listing.getTwitterAccessToken()))  	   existingConf.setTwitterAccessToken(listing.getTwitterAccessToken());
		if(nonNull(listing.getTwitterAccessTokenSecret())) existingConf.setTwitterAccessTokenSecret(listing.getTwitterAccessTokenSecret());
		if(nonNull(listing.getShowBundles())) 	  		   existingConf.setShowBundles(listing.getShowBundles());
		if(nonNull(listing.getSolanaOnOpensea()))  	   	   existingConf.setSolanaOnOpensea(listing.getSolanaOnOpensea());
		if(nonNull(listing.getExcludeDiscord()))   		   existingConf.setExcludeDiscord(listing.getExcludeDiscord());
		if(nonNull(listing.getExcludeTwitter()))   		   existingConf.setExcludeTwitter(listing.getExcludeTwitter());
		if(nonNull(listing.getExcludeLooksrare())) 		   existingConf.setExcludeLooksrare(listing.getExcludeLooksrare());
		if(nonNull(listing.getExcludeOpensea()))   		   existingConf.setExcludeOpensea(listing.getExcludeOpensea());
		if(nonNull(listing.getLastOpenseaId()))			   existingConf.setLastOpenseaId(listing.getLastOpenseaId());
		if(nonNull(listing.getLastLooksId()))			   existingConf.setLastLooksId(listing.getLastLooksId());
		if(nonNull(listing.getActive())) 		   		   existingConf.setActive(listing.getActive());
		// Validate Access
		if(nonNull(existingConf.getDiscordToken()) && nonNull(existingConf.getDiscordChannelId()))
			new DiscordBot(existingConf.getDiscordToken(), existingConf.getDiscordChannelId());
		// Save
		listingsRepository.save(existingConf);
		// Build response
		return ResponseEntity.noContent().build();
	}
	
	@Transactional
	public ResponseEntity<String> deleteListings(long listingsId) {
		// Ensure exists or throw error
		checkListingsExists(listingsId);
		// Delete
		listingsRepository.deleteById(listingsId);
		// Build response
		return ResponseEntity.noContent().build();
	}
	
	/*
	 * Helper function checks for existence in the database
	 */
	private void checkListingsExists(long listingsId) {
		// Check existence
		if(!listingsRepository.existsById(listingsId))
			throw new ResourceNotFoundException(String.format(listingsNotFoundException, listingsId));
	}

}
