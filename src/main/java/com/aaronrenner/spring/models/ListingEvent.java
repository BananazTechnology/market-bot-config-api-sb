package com.aaronrenner.spring.models;

import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import com.aaronrenner.spring.utils.MarketPlace;
import com.aaronrenner.spring.utils.RarityEngine;
import com.aaronrenner.spring.utils.Ticker;
import lombok.Data;
import lombok.ToString;

@ToString(includeFieldNames=true)
@Data
@Entity
@Table(name = "listingEvent")
public class ListingEvent {
	
	@Id
	private long   id;
	private String name;
	private Instant createdDate;
	private Instant startTime;
	private Instant endTime;
	private String tokenId;
	private String collectionName;
	private String collectionImageUrl;
	private String slug;
	private String imageUrl;
	private String permalink;
	private int	   quantity;
	private String sellerWalletAddy;
	private String sellerName;
	private String displayNameOutput;
	private String rarity;
	private String rarityRedirect;
	private BigDecimal listingPriceInCrypto;
	private BigDecimal listingPriceInUsd;
	private String listingAmoutOutput;
	@Enumerated( EnumType.STRING )
	private Ticker cryptoPaymentType;
	@Enumerated( EnumType.STRING )
	private RarityEngine engine;
	@Enumerated( EnumType.STRING )
	private MarketPlace market;
	// These last few items are for the consumer
	private long contractId;
	private String consumedBy;
	private boolean consumed;

}
