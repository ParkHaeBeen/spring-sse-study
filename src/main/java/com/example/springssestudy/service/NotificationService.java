package com.example.springssestudy.service;

import com.example.springssestudy.domain.Comment;
import com.example.springssestudy.domain.Memo;
import com.example.springssestudy.domain.Notice;
import com.example.springssestudy.repository.EmitterRepository;
import com.example.springssestudy.repository.MemoRepository;
import com.example.springssestudy.repository.NoticeRepository;
import com.example.springssestudy.security.jwt.JwtUtils;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Service
public class NotificationService {

  private final NoticeRepository noticeRepository;
  private final MemoRepository memoRepository;
  private final EmitterRepository emitterRepository;
  private final JwtUtils jwtUtils;

  public void notifyAddCommentEvent(Comment comment, Long memoId) {
    Memo memo = memoRepository.findById(memoId).get();
    Long userId = memo.getUser().getId();
    noticeRepository.save(Notice.builder()
                                .user(memo.getUser())
                                .infoId(memoId)
                                .build());
    SseEmitter sseEmitter = emitterRepository.findByUserId(userId);
    if(sseEmitter == null){
      sseEmitter = save(userId);
    }

    try {
      sseEmitter.send(SseEmitter.event().name("addComment").data(comment.getContent()));
    } catch (Exception e) {
      emitterRepository.deleteByUserId(userId);
    }
  }

  public SseEmitter save(String token){
    Long userId = jwtUtils.getUserIdFromToken(token);
    SseEmitter emitter = emitterRepository.save(userId);
    try {
      emitter.send(SseEmitter.event().name("connect").data("sse 연결됨"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    emitter.onCompletion(() -> emitterRepository.deleteByUserId(userId));
    emitter.onTimeout(() -> emitterRepository.deleteByUserId(userId));
    emitter.onError((e) -> emitterRepository.deleteByUserId(userId));

    return emitter;
  }

  public SseEmitter save(Long userId){
    SseEmitter emitter = emitterRepository.save(userId);
    try {
      emitter.send(SseEmitter.event().name("connect").data("sse 연결됨"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    emitter.onCompletion(() -> emitterRepository.deleteByUserId(userId));
    emitter.onTimeout(() -> emitterRepository.deleteByUserId(userId));
    emitter.onError((e) -> emitterRepository.deleteByUserId(userId));

    return emitter;
  }
}
