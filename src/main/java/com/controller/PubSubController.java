package com.controller;

import com.sub.LocalEmitter;
import com.sub.RedisMessageSubscriber;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/message")
@CrossOrigin
public class PubSubController {

  @Autowired
  RedisMessageSubscriber redisMessageSubscriber;

  @Autowired
  RedisTemplate redisTemplate;


  private static final Logger LOGGER = LoggerFactory.getLogger(PubSubController.class);

  @GetMapping(value = "/publish/{id}")
  public SseEmitter publish(@PathVariable int id) throws InterruptedException, IOException {

    SseEmitter emitter = new SseEmitter(180000L);
    LocalEmitter.requestMap.put(id, emitter);
    return emitter;
  }

  @GetMapping(value = "/complete/{id}")
  public String complete(@PathVariable int id) {
    redisTemplate.convertAndSend("request", id);
    return "done!" + redisMessageSubscriber.getCount();
  }
}
