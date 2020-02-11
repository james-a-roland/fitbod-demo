package com.fitbod.jroland.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.Jedis;

import java.net.URI;
import java.util.Optional;

@Configuration
public class JedisConfig {

  @Autowired
  private Environment environment;

  @Bean
  Jedis getJedis() {
    Optional<URI> jedisURI = Optional.ofNullable(environment.getProperty("REDISTOGO_URL")).map(URI::create);
    String host = jedisURI.map(URI::getHost).orElse(environment.getProperty("fitbod.default.redis.host"));
    int port = jedisURI.map(URI::getPort).orElse(Integer.valueOf(environment.getProperty("fitbod.default.redis.port")));
    Jedis jedis = new Jedis(host, port);
    return jedis;
  }
}
