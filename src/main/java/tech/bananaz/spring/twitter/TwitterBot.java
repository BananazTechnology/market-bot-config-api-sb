package tech.bananaz.spring.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.redouane59.twitter.TwitterClient;
import com.github.redouane59.twitter.signature.TwitterCredentials;
import tech.bananaz.exceptions.BadRequestException;
import static java.util.Objects.isNull;

public class TwitterBot {
	
	// Errors
	private String SERVER_ERROR = "Failed twitter login or connection! Ensure you have provided all 4 proper credentials";
	private String INVALID_CREDS = "Unable to verify your twitter credentials, login failed";
	private static final Logger LOGGER  = LoggerFactory.getLogger(TwitterBot.class);
	
	public TwitterBot(String accessToken, String accessTokenSecret, String apiKey, String apiKeySecret) {
		TwitterClient bot = null;
		
		try {
			bot = new TwitterClient(TwitterCredentials.builder()
                .accessToken(accessToken)
                .accessTokenSecret(accessTokenSecret)
                .apiKey(apiKey)
                .apiSecretKey(apiKeySecret)
                .build());
		} catch (Exception e) {
			LOGGER.error(String.format(SERVER_ERROR, e.getMessage()));
        	throw new BadRequestException(SERVER_ERROR);
		}

		if(isNull(bot.getBearerToken())) {
        	throw new BadRequestException(INVALID_CREDS);
		}
	}
}