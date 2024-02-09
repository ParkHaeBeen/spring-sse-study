package com.example.springssestudy.service.notice;

import com.example.springssestudy.domain.Notice;
import com.example.springssestudy.dto.MessageDto;
import com.example.springssestudy.repository.EmitterRepository;
import com.example.springssestudy.repository.NoticeRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class RedisMessageService {

  private static final String CHANNEL_PREFIX = "channel:";
  private final RedisMessageListenerContainer container;
  private final EmitterRepository emitterRepository;
  private final RedisTemplate<String, MessageDto> jsonRedisTemplate;
  private final RedisSubscriber subscriber;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void send(MessageDto event){
    System.out.println("여기까지는 오니???");
    Long userId=event.getUserId();
    jsonRedisTemplate.convertAndSend(getChannelName(userId),event);
  }

  public SseEmitter subscribe(Long userId){
    //연결되었는지 확인 메세지 전송
    SseEmitter emitter = emitterRepository.save(userId);
    try {
      emitter.send(SseEmitter.event().id(userId.toString()).name("connect").data("연결되었습니다"));
      container.addMessageListener(subscriber, ChannelTopic.of(getChannelName(userId)));
      checkEmitterStatus(emitter, subscriber,userId);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return emitter;
  }

  private String getChannelName(Long userId){
    return CHANNEL_PREFIX+userId;
  }

  private void checkEmitterStatus(final SseEmitter emitter, final MessageListener messageListener, Long userId) {
    emitter.onCompletion(() -> {
      this.container.removeMessageListener(messageListener,ChannelTopic.of(getChannelName(userId)));
    });
    emitter.onTimeout(() -> {
      this.container.removeMessageListener(messageListener,ChannelTopic.of(getChannelName(userId)));
    });
  }
}
