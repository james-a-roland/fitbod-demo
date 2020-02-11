package com.fitbod.jroland.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.util.Optional;

@Configuration
public class JedisConfig {

  @Autowired
  private Environment environment;

  @Bean
  JedisPool getJedisPool() {
    Optional<URI> jedisUriOptional = Optional.ofNullable(environment.getProperty("REDISTOGO_URL")).map(URI::create);
    if (jedisUriOptional.isPresent()) {
      URI jedisURI = jedisUriOptional.get();
      return new JedisPool(new JedisPoolConfig(),
                           jedisURI.getHost(),
                           jedisURI.getPort(),
                           Protocol.DEFAULT_TIMEOUT,
                           jedisURI.getUserInfo().split(":", 2)[1]);
    } else {
      String host = environment.getProperty("fitbod.default.redis.host");
      int port = Integer.valueOf(environment.getProperty("fitbod.default.redis.port"));
      return new JedisPool(host, port);
    }
  }
}
