package com.config;

import com.sub.RedisMessageSubscriber;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    ChannelTopic topic() {
      return new ChannelTopic("request");
    }

    @Autowired
    RedisMessageSubscriber redisMessageSubscriber;

    @Bean
    MessageListenerAdapter messageListener( ) {
      return new MessageListenerAdapter(redisMessageSubscriber);
    }

  @Bean
  Map<Integer, String> requestMap( ) {
    return new ConcurrentHashMap<>();
  }

  @Bean
  RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
      RedisMessageListenerContainer container
          = new RedisMessageListenerContainer();
      container.setConnectionFactory(redisConnectionFactory);
      container.addMessageListener(messageListener(), topic());
      return container;
    }

  @Bean
  public RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    final RedisTemplate<String, Integer> template = new RedisTemplate<String, Integer>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setValueSerializer(new Jackson2JsonRedisSerializer<Integer>(Integer.class));
    return template;
  }
  }