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
	@Column(columnDefinition = "VARCHAR(75)")
	private String     name;
	private Instant    createdDate;
	private Instant    startTime;
	private Instant    endTime;
	private String     tokenId;
	@Column(columnDefinition = "VARCHAR(75)")
	private String     collectionName;
	private String     collectionImageUrl;
	@Column(columnDefinition = "VARCHAR(50)")
	private String     slug;
	private String     imageUrl;
	@Column(columnDefinition = "VARCHAR(127)")
	private String     permalink;
	private int	       quantity;
	@Column(columnDefinition = "VARCHAR(50)")
	private String     sellerWalletAddy;
	@Column(columnDefinition = "VARCHAR(50)")
	private String     sellerName;
	@Column(columnDefinition = "VARCHAR(127)")
	private String     sellerUrl;
	@Column(columnDefinition = "VARCHAR(50)")
	private String     displayNameOutput;
	@Column(columnDefinition = "VARCHAR(6)")
	private String     rarity;
	@Column(columnDefinition = "VARCHAR(50)")
	private String     rarityRedirect;
	@Column(columnDefinition = "VARCHAR(25)")
	private BigDecimal listingPriceInCrypto;
	@Column(columnDefinition = "VARCHAR(25)")
	private BigDecimal listingPriceInUsd;
	@Column(columnDefinition = "VARCHAR(75)")
	private String     listingAmoutOutput;
	@Enumerated( EnumType.STRING )
	@Column(columnDefinition = "VARCHAR(6)")
	private Ticker     cryptoPaymentType;
	@Enumerated( EnumType.STRING )
	@Column(columnDefinition = "VARCHAR(50)")
	private RarityEngine engine;
	@Enumerated( EnumType.STRING )
	@Column(columnDefinition = "VARCHAR(50)")
	private MarketPlace  market;
	// These last few items are for the consumer
	private long         contractId;
	@Column(columnDefinition = "VARCHAR(50)")
	private String       consumedBy;
	@Column(nullable = false, columnDefinition="TINYINT(1) UNSIGNED DEFAULT 0")
	private boolean      consumed;

}
