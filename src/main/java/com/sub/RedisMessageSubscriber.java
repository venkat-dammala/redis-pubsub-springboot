package com.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class RedisMessageSubscriber implements MessageListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageSubscriber.class);

  private AtomicInteger counter = new AtomicInteger();
  ObjectMapper objectMapper = new ObjectMapper();


  public int getCount() {
    return counter.get();
  }

  @Override
  public void onMessage(org.springframework.data.redis.connection.Message message, byte[] bytes) {
    try {
      int id = objectMapper.readValue(message.toString(), Integer.class);
      LOGGER.info("received request id {}", id);
      if(LocalEmitter.requestMap.containsKey(id)) {
        SseEmitter se =  LocalEmitter.requestMap.get(id);
        se.send(id);
        se.complete();
        LOGGER.info("completed request id {}", id);
        LocalEmitter.requestMap.remove(id);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}