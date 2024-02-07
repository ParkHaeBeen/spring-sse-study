package com.example.springssestudy.service;

import static com.example.springssestudy.controller.SseController.sseEmitters;

import com.example.springssestudy.domain.Memo;
import com.example.springssestudy.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Service
public class NotificationService {

  private final MemoRepository memoRepository;

  public void notifyAddCommentEvent(Memo memo) {

    Long userId = memo.getUser().getId();

    if (sseEmitters.containsKey(userId)) {
      SseEmitter sseEmitter = sseEmitters.get(userId);
      try {
        sseEmitter.send(SseEmitter.event().name("addComment").data("댓글이 달렸습니다!!!!!"));
      } catch (Exception e) {
        sseEmitters.remove(userId);
      }
    }
  }
}
