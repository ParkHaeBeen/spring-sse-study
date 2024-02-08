package com.example.springssestudy.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {
  private final Map <Long, SseEmitter> emitters = new ConcurrentHashMap <>();

  public SseEmitter save(Long userId){
    SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
    emitters.put(userId,sseEmitter);
    return sseEmitter;
  }

  public SseEmitter findByUserId(Long userId){
    if(!emitters.containsKey(userId)){
      return null;
    }
    return emitters.get(userId);
  }

  public void deleteByUserId(Long userId){
    emitters.remove(userId);
  }
}
