package com.thedariusz.warnme;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thedariusz.warnme.twitter.api.InMemoryMeteoAlertDao;
import com.thedariusz.warnme.twitter.api.MeteoAlertDao;
import com.thedariusz.warnme.twitter.api.MeteoAlertMapper;
import com.thedariusz.warnme.twitter.api.MeteoAlertService;
import com.thedariusz.warnme.twitter.api.TweetService;
import com.thedariusz.warnme.twitter.api.TwitterClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
public class WarnMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarnMeApplication.class, args);
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return objectMapper;
	}

	@Bean
	@Primary
	public MeteoAlertDao inMemoryMeteoAlertDao() {
		return new InMemoryMeteoAlertDao();
	}

	@Bean
	public MeteoAlertDao mySqlMeteoAlertDao() {
		return new MySqlMeteoAlertDao();
	}

	@Bean
	public MeteoAlertService meteoAlertService(MeteoAlertDao inMemoryMeteoAlertDao) {
		return new MeteoAlertService(inMemoryMeteoAlertDao);
	}

	@Bean
	public TwitterClient twitterClient() {
		return new TwitterClient();
	}

	@Bean
	public TweetService tweetService(MeteoAlertService meteoAlertService, TwitterClient twitterClient) {
		return new TweetService(meteoAlertService, twitterClient, new MeteoAlertMapper());
	}


}