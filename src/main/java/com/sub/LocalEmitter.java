package com.sub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class LocalEmitter {

  public static Map<Integer, SseEmitter> requestMap = new ConcurrentHashMap<>();

}
