package tech.bananaz.spring.models;

import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;
import tech.bananaz.spring.utils.MarketPlace;
import tech.bananaz.spring.utils.RarityEngine;
import tech.bananaz.spring.utils.Ticker;

@ToString(includeFieldNames=true)
@Data
@Entity
@Table(name = "listing_event")
public class ListingEvent {
	
	@Id
	private long       id;
	private String     name;
	private Instant    createdDate;
	private Instant    startTime;
	private Instant    endTime;
	private String     tokenId;
	private String     collectionName;
	private String     collectionImageUrl;
	private String     slug;
	private String     imageUrl;
	private String     permalink;
	private int	       quantity;
	private String     sellerWalletAddy;
	private String     sellerName;
	private String     sellerUrl;
	private String     displayNameOutput;
	private String     rarity;
	private String     rarityRedirect;
	private BigDecimal listingPriceInCrypto;
	private BigDecimal listingPriceInUsd;
	private String     listingAmoutOutput;
	@Enumerated( EnumType.STRING )
	private Ticker     cryptoPaymentType;
	@Enumerated( EnumType.STRING )
	private RarityEngine engine;
	@Enumerated( EnumType.STRING )
	private MarketPlace  market;
	// These last few items are for the consumer
	private long         contractId;
	private String       consumedBy;
	@Column(nullable = false, columnDefinition="TINYINT(1) UNSIGNED DEFAULT 0")
	private boolean      consumed;

}
