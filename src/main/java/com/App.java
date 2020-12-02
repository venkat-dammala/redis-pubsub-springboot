package com;


import com.dto.Message;
import com.sub.RedisMessageSubscriber;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@EnableScheduling
@SpringBootApplication
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws InterruptedException {

    ApplicationContext ctx = SpringApplication.run(App.class, args);

    StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
    RedisMessageSubscriber redisMessageSubscriber = ctx.getBean(RedisMessageSubscriber.class);
  }
}