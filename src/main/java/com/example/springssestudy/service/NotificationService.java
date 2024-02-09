package com.example.springssestudy.service;

import com.example.springssestudy.domain.Comment;
import com.example.springssestudy.domain.Memo;
import com.example.springssestudy.domain.Notice;
import com.example.springssestudy.dto.MessageDto;
import com.example.springssestudy.repository.EmitterRepository;
import com.example.springssestudy.repository.MemoRepository;
import com.example.springssestudy.repository.NoticeRepository;
import com.example.springssestudy.security.jwt.JwtUtils;
import com.example.springssestudy.service.notice.RedisMessageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Service
public class NotificationService {

  private final NoticeRepository noticeRepository;
  private final MemoRepository memoRepository;
  private final JwtUtils jwtUtils;
  private final ApplicationEventPublisher eventPublisher;
  private final RedisMessageService redisMessageService;

  @Transactional
  public void notifyAddCommentEvent(Comment comment, Long memoId,Long loginId) {
    Memo memo = memoRepository.findById(memoId).get();
    Long userId = memo.getUser().getId();
    Notice save = noticeRepository.save(Notice.builder()
        .user(memo.getUser())
        .infoId(memoId)
        .build());
    System.out.println("알림아 가야해!!!!!");
    if(loginId!=userId) {
      eventPublisher.publishEvent(MessageDto.builder()
          .noticeType(save.getNoticeType())
          .content(comment.getContent())
          .id(save.getId())
          .userId(userId)
          .build());
    }
  }

  public SseEmitter save(String token){
    Long userId = jwtUtils.getUserIdFromToken(token);
    return redisMessageService.subscribe(userId);
  }

}
