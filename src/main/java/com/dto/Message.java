package com.dto;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class Message {

  SseEmitter sseEmitter;

  Integer id;

  public SseEmitter getSseEmitter() {
    return sseEmitter;
  }

  public void setSseEmitter(SseEmitter sseEmitter) {
    this.sseEmitter = sseEmitter;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
