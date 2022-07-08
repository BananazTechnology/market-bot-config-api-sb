package com.aaronrenner.spring.discord;

import java.util.Collection;
import java.util.Optional;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.*;
import org.javacord.api.entity.permission.Permissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aaronrenner.spring.exceptions.BadRequestException;
import com.aaronrenner.spring.exceptions.InternalServerError;

public class DiscordBot {
	
	// Errors
	private String SERVER_ERROR = "Failed discord starting bot! Exception: %s";
	private String NOT_IN_CHANNEL = "Your bot is not part of a channel with id or name of %s, try inviting it with %s";
	private String BOUNDS_CHANNEL = "The selected channel with id or name of %s has to many matches";
	private String CANNOT_ACCESS_CHANNEL = "Your bot cannot see or write the channel with id or name of %s";
	private static final Logger LOGGER  = LoggerFactory.getLogger(DiscordBot.class);
	
	public DiscordBot(String token, String channel) {
		DiscordApi bot = null;
		
		try {
			// Start Discord connection
	        bot = new DiscordApiBuilder()
	        					.setToken(token)
	        					.login()
	        					.join();
	        // End
		} catch (Exception e) {
			LOGGER.error(String.format(SERVER_ERROR, e.getMessage()));
        	throw new InternalServerError(String.format(SERVER_ERROR, e.getMessage()));
		}
		//Now that we know the bot is created
        String inviteLink = bot.createBotInvite(Permissions.fromBitmask(19520));
        
        Collection<ServerTextChannel> findByName = bot.getServerTextChannelsByNameIgnoreCase(channel);
        Optional<ServerTextChannel> findById     = bot.getServerTextChannelById(channel);
        ServerTextChannel selectedChannel 		 = null;
        
        if(findByName.size() < 1 && findById.isEmpty()) throw new BadRequestException(String.format(NOT_IN_CHANNEL, channel, inviteLink));
        if(findByName.size() > 1) throw new BadRequestException(String.format(BOUNDS_CHANNEL, channel));
        if(findByName.size() > 0) {
        	for(ServerTextChannel c : findByName) {
        		selectedChannel = c;
        		break;
        	}
        }
        if(findById.isPresent()) selectedChannel = findById.get();
        
        /// Ensure channel permissions and proper type
        if(!selectedChannel.canYouSee()) throw new BadRequestException(String.format(CANNOT_ACCESS_CHANNEL, channel));
        if(!selectedChannel.canYouWrite()) throw new BadRequestException(String.format(CANNOT_ACCESS_CHANNEL, channel));
        
        bot.disconnect();
	}
}