package tech.bananaz.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bananaz.enums.RarityEngine;
import tech.bananaz.exceptions.ResourceNotFoundException;
import tech.bananaz.models.Sale;
import tech.bananaz.repositories.SaleConfigPagingRepository;
import tech.bananaz.spring.discord.DiscordBot;
import tech.bananaz.spring.twitter.TwitterBot;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static tech.bananaz.utils.EncryptionUtils.encryptSale;
import static tech.bananaz.utils.EncryptionUtils.decryptSale;

@Service
public class SalesService {
	
	// Security
	@Value("${bot.encryptionKey}")
	private String key;
	
	// Assets for Listing Config
	@Autowired
	SaleConfigPagingRepository salePagingRepository;
	private final String salesNotFoundException = "Sales with the value %s was not found";
	
	@Transactional
	public URI createSales(HttpServletRequest request, Sale sale) throws Exception {
		// Set defaults
		if(isNull(sale.getActive())) 		  	 sale.setActive(true);
		if(isNull(sale.getRarityEngine()))	 	 sale.setRarityEngine(RarityEngine.NONE);
		if(isNull(sale.getIsSlug()))   	 		 sale.setIsSlug(false);
		if(isNull(sale.getExcludeDiscord()))   	 sale.setExcludeDiscord(false);
		if(isNull(sale.getExcludeTwitter()))   	 sale.setExcludeTwitter(false);
		if(isNull(sale.getExcludeLooksrare())) 	 sale.setExcludeLooksrare(false);
		if(isNull(sale.getExcludeOpensea()))   	 sale.setExcludeOpensea(false);
		if(isNull(sale.getShowBundles())) 	  	 sale.setShowBundles(true);
		if(isNull(sale.getSolanaOnOpensea())) 	 sale.setSolanaOnOpensea(false);
		if(isNull(sale.getPolygonOnOpensea())) 	 sale.setPolygonOnOpensea(false);
		
		// If SOL or POLY then address is always a slug
		if(sale.getSolanaOnOpensea())			 sale.setIsSlug(true);
		if(sale.getPolygonOnOpensea())			 sale.setIsSlug(true);
		
		// Validate Access
		if(nonNull(sale.getDiscordToken()) && nonNull(sale.getDiscordChannelId()))
			new DiscordBot(sale.getDiscordToken(), sale.getDiscordChannelId());
		
		// Validate Twitter Access
		if(nonNull(sale.getTwitterApiKey()) || 
			nonNull(sale.getTwitterApiKeySecret()) || 
			nonNull(sale.getTwitterAccessToken()) ||
			nonNull(sale.getTwitterAccessTokenSecret())) {
			new TwitterBot(sale.getTwitterAccessToken(), sale.getTwitterAccessTokenSecret(), sale.getTwitterApiKey(), sale.getTwitterApiKeySecret());
		}
		
		// Security
		sale = encryptSale(this.key, sale);
		
		// Run function
		Sale newConf = salePagingRepository.save(sale);
		
		// Build response
		return URI.create(request.getRequestURL()+"/"+newConf.getId().toString());
	}
	
	@Transactional
	public Object readAllSales(int page, int limit, Boolean showAll) {
		// If asking for the older way of showing all
		if(showAll) return salePagingRepository.findAll();
		// Everything else paging
		Pageable where = PageRequest.of(page, limit);
		return salePagingRepository.findAll(where);
	}
	
	@Transactional
	public Sale readSales(long salesId) {
		// Ensure exists or throw error
		checkSalesExists(salesId);
		// Build response
		return salePagingRepository.findById(salesId).get();
	}
	
	@Transactional
	public void updateSales(long salesId, Sale sale) throws Exception {
		// Ensure exists or throw error
		checkSalesExists(salesId);
		
		// Get existing
		Sale existingConf = salePagingRepository.findById(salesId).get();
		
		// Decrypt to update - only lasts till the security block about 15 lines below];
		existingConf 	  = decryptSale(this.key, existingConf);
		
		// Update provided
		if(nonNull(sale.getContractAddress())) 	 	    existingConf.setContractAddress(sale.getContractAddress());
		if(nonNull(sale.getInterval())) 		  	    existingConf.setInterval(sale.getInterval());
		if(nonNull(sale.getRarityEngine()))			    existingConf.setRarityEngine(sale.getRarityEngine());
		if(nonNull(sale.getDiscordMessageColor())) 	    existingConf.setDiscordMessageColor(sale.getDiscordMessageColor());
		if(nonNull(sale.getDiscordServerId())) 	  	    existingConf.setDiscordServerId(sale.getDiscordServerId());
		if(nonNull(sale.getDiscordChannelId())) 	    existingConf.setDiscordChannelId(sale.getDiscordChannelId());
		if(nonNull(sale.getDiscordToken())) 	  	  	existingConf.setDiscordToken(sale.getDiscordToken());
		if(nonNull(sale.getTwitterApiKey())) 	  	   	existingConf.setTwitterApiKey(sale.getTwitterApiKey());
		if(nonNull(sale.getTwitterApiKeySecret())) 	   	existingConf.setTwitterApiKeySecret(sale.getTwitterApiKeySecret());
		if(nonNull(sale.getTwitterAccessToken()))  	   	existingConf.setTwitterAccessToken(sale.getTwitterAccessToken());
		if(nonNull(sale.getTwitterAccessTokenSecret())) existingConf.setTwitterAccessTokenSecret(sale.getTwitterAccessTokenSecret());
		if(nonNull(sale.getTwitterMessageTemplate()))   existingConf.setTwitterMessageTemplate(sale.getTwitterMessageTemplate());
		if(nonNull(sale.getShowBundles())) 	  		   	existingConf.setShowBundles(sale.getShowBundles());
		if(nonNull(sale.getExcludeDiscord()))   		existingConf.setExcludeDiscord(sale.getExcludeDiscord());
		if(nonNull(sale.getExcludeTwitter()))   		existingConf.setExcludeTwitter(sale.getExcludeTwitter());
		if(nonNull(sale.getExcludeLooksrare())) 		existingConf.setExcludeLooksrare(sale.getExcludeLooksrare());
		if(nonNull(sale.getExcludeOpensea()))   		existingConf.setExcludeOpensea(sale.getExcludeOpensea());
		if(nonNull(sale.getIsSlug())) 	  			    existingConf.setIsSlug(sale.getIsSlug());
		if(nonNull(sale.getSolanaOnOpensea())) 	  		existingConf.setSolanaOnOpensea(sale.getSolanaOnOpensea());
		if(nonNull(sale.getPolygonOnOpensea())) 	  	existingConf.setPolygonOnOpensea(sale.getPolygonOnOpensea());
		if(nonNull(sale.getActive())) 		   		    existingConf.setActive(sale.getActive());
		
		// If SOL or POLY then address is always a slug
		if(nonNull(sale.getSolanaOnOpensea()))
			if(sale.getSolanaOnOpensea())				existingConf.setIsSlug(true);
		if(nonNull(sale.getPolygonOnOpensea()))
			if(sale.getPolygonOnOpensea())			 	existingConf.setIsSlug(true);
		
		// Validate Discord Access
		if(nonNull(sale.getDiscordToken()) || nonNull(sale.getDiscordChannelId())) {
			String discordToken = (nonNull(sale.getDiscordToken()))? sale.getDiscordToken() : existingConf.getDiscordToken();
			String discordChannel = (nonNull(sale.getDiscordChannelId())) ? sale.getDiscordChannelId() : existingConf.getDiscordChannelId();
			new DiscordBot(discordToken, discordChannel);
		}
		
		// Validate Twitter Access
		if(nonNull(sale.getTwitterApiKey()) || 
			nonNull(sale.getTwitterApiKeySecret()) || 
			nonNull(sale.getTwitterAccessToken()) ||
			nonNull(sale.getTwitterAccessTokenSecret())) {
			
			String apiKey = (nonNull(sale.getTwitterApiKey()))? sale.getTwitterApiKey() : existingConf.getTwitterApiKey();
			String apiKeySecret = (nonNull(sale.getTwitterApiKeySecret())) ? sale.getTwitterApiKeySecret() : existingConf.getTwitterApiKeySecret();
			String accessToken = (nonNull(sale.getTwitterAccessToken())) ? sale.getTwitterAccessToken() : existingConf.getTwitterAccessToken();
			String accessTokenSecret = (nonNull(sale.getTwitterAccessTokenSecret())) ? sale.getTwitterAccessTokenSecret() : existingConf.getTwitterAccessTokenSecret();
			new TwitterBot(accessToken, accessTokenSecret, apiKey, apiKeySecret);
		}
		
		// Security
		existingConf = encryptSale(this.key, existingConf);
		
		// Save
		salePagingRepository.save(existingConf);
	}
	
	@Transactional
	public void deleteSales(long salesId) {
		// Ensure exists or throw error
		checkSalesExists(salesId);
		// Delete
		salePagingRepository.deleteById(salesId);
	}
	
	/*
	 * Helper function checks for existence in the database
	 */
	private void checkSalesExists(long salesId) {
		// Check existence
		if(!salePagingRepository.existsById(salesId))
			throw new ResourceNotFoundException(String.format(salesNotFoundException, salesId));
	}

}
