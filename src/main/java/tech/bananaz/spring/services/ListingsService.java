package tech.bananaz.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bananaz.spring.discord.DiscordBot;
import tech.bananaz.exceptions.ResourceNotFoundException;
import tech.bananaz.models.Listing;
import tech.bananaz.repositories.ListingConfigPagingRepository;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class ListingsService {
	
	// Assets for Listing Config
	@Autowired
	ListingConfigPagingRepository listPagingRepository;
	
	private final String listingsNotFoundException = "Listings with the value %s was not found";
	
	@Transactional
	public URI createListings(HttpServletRequest request, Listing listing) {
		// Set defaults
		if(isNull(listing.getActive())) 		  	 listing.setActive(true);
		if(isNull(listing.getAutoRarity())) 	  	 listing.setAutoRarity(false);
		if(isNull(listing.getIsSlug()))   	 		 listing.setIsSlug(false);
		if(isNull(listing.getExcludeDiscord()))   	 listing.setExcludeDiscord(false);
		if(isNull(listing.getExcludeTwitter()))   	 listing.setExcludeTwitter(false);
		if(isNull(listing.getExcludeLooksrare())) 	 listing.setExcludeLooksrare(false);
		if(isNull(listing.getExcludeOpensea()))   	 listing.setExcludeOpensea(false);
		if(isNull(listing.getShowBundles())) 	  	 listing.setShowBundles(true);
		if(isNull(listing.getSolanaOnOpensea())) 	 listing.setSolanaOnOpensea(false);
		// If SOL then address is always a slug
		if(listing.getSolanaOnOpensea())			 listing.setIsSlug(true);
		
		// Validate Access
		if(nonNull(listing.getDiscordToken()) && nonNull(listing.getDiscordChannelId()))
			new DiscordBot(listing.getDiscordToken(), listing.getDiscordChannelId());
		// Run function
		Listing newConf = listPagingRepository.save(listing);
		// Build response
		return URI.create(request.getRequestURL()+"/"+newConf.getId().toString());
	}
	
	@Transactional
	public Object readAllListings(int page, int limit, Boolean showAll) {
		// If asking for the older way of showing all
		if(showAll) return listPagingRepository.findAll();
		// Everything else paging
		Pageable where = PageRequest.of(page, limit);
		return listPagingRepository.findAll(where);
	}
	
	@Transactional
	public Listing readListings(long listingsId) {
		// Ensure exists or throw error
		checkListingsExists(listingsId);
		// Build response
		return listPagingRepository.findById(listingsId).get();
	}
	
	@Transactional
	public void updateListings(long listingsId, Listing listing) {
		// Ensure exists or throw error
		checkListingsExists(listingsId);
		// Get existing
		Listing existingConf = listPagingRepository.findById(listingsId).get();
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
		if(nonNull(listing.getActive())) 		   		   existingConf.setActive(listing.getActive());
		// Validate Access
		if(nonNull(existingConf.getDiscordToken()) && nonNull(existingConf.getDiscordChannelId()))
			new DiscordBot(existingConf.getDiscordToken(), existingConf.getDiscordChannelId());
		// Save
		listPagingRepository.save(existingConf);
	}
	
	@Transactional
	public void deleteListings(long listingsId) {
		// Ensure exists or throw error
		checkListingsExists(listingsId);
		// Delete
		listPagingRepository.deleteById(listingsId);
	}
	
	/*
	 * Helper function checks for existence in the database
	 */
	private void checkListingsExists(long listingsId) {
		// Check existence
		if(!listPagingRepository.existsById(listingsId))
			throw new ResourceNotFoundException(String.format(listingsNotFoundException, listingsId));
	}

}
