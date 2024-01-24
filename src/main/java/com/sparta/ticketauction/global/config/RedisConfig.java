package com.sparta.ticketauction.global.config;

import java.time.Duration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer()
			.setAddress("redis://" + host + ":" + port);
		return Redisson.create(config);
	}

	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory() {
		if (activeProfile.equals("local")) {
			return new LettuceConnectionFactory(host, port);
		} else {
			RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
			clusterConfiguration.clusterNode(host, port);
			LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
				.clientOptions(ClientOptions.builder()
					.socketOptions(SocketOptions.builder()
						.connectTimeout(Duration.ofMillis(5000L)).build())
					.build())
				.commandTimeout(Duration.ofSeconds(5000L))
				.build();
			return new LettuceConnectionFactory(clusterConfiguration, clientConfiguration);
		}
	}

	@Bean
	public RedisTemplate<String, Object> lettuceTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		redisTemplate.setConnectionFactory(lettuceConnectionFactory());

		return redisTemplate;
	}

	@Bean
	public RedisMessageListenerContainer redisContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(lettuceConnectionFactory());
		return container;
	}
}
