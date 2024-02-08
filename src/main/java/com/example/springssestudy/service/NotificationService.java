package com.example.springssestudy.service;

import com.example.springssestudy.domain.Memo;
import com.example.springssestudy.repository.EmitterRepository;
import com.example.springssestudy.repository.MemoRepository;
import com.example.springssestudy.security.jwt.JwtUtils;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Service
public class NotificationService {

  private final MemoRepository memoRepository;
  private final EmitterRepository emitterRepository;
  private final JwtUtils jwtUtils;

  public void notifyAddCommentEvent(Memo memo) {
    Long userId = memo.getUser().getId();
    SseEmitter sseEmitter = emitterRepository.findByUserId(userId);
    if(sseEmitter != null){
      try {
        sseEmitter.send(SseEmitter.event().name("addComment").data("댓글이 달렸습니다!!!!!"));
      } catch (Exception e) {
        emitterRepository.deleteByUserId(userId);
      }
    }
  }

  public SseEmitter save(String token){
    Long userId = jwtUtils.getUserIdFromToken(token);
    SseEmitter emitter = emitterRepository.save(userId);
    try {
      emitter.send(SseEmitter.event().name("connect"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    emitter.onCompletion(() -> emitterRepository.deleteByUserId(userId));
    emitter.onTimeout(() -> emitterRepository.deleteByUserId(userId));
    emitter.onError((e) -> emitterRepository.deleteByUserId(userId));

    return emitter;
  }
}
