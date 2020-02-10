package com.fitbod.jroland.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisConfig {

  @Autowired
  private Environment environment;

  @Bean
  Jedis getJedis() {
    String host = environment.getProperty("fitbod.redis.host");
    int port = Integer.valueOf(environment.getProperty("fitbod.redis.port"));
    Jedis jedis = new Jedis(host, port);
    return jedis;
  }
}
