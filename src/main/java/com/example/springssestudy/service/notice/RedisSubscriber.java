package com.example.springssestudy.service.notice;

import com.example.springssestudy.domain.Notice;
import com.example.springssestudy.dto.MessageDto;
import com.example.springssestudy.repository.EmitterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {
  private final ObjectMapper objectMapper;
  private final EmitterRepository emitterRepository;
  @Override
  public void onMessage(Message message , byte[] pattern) {

    log.info("RedisSubscriber onMessage");

    MessageDto messageDto = serialize(message);
    SseEmitter sseEmitter=emitterRepository.findByUserId(messageDto.getUserId());
    if(sseEmitter==null){
      sseEmitter = emitterRepository.save(messageDto.getUserId());
    }
    sendToClient(sseEmitter,messageDto.getUserId(), messageDto);
  }
  private MessageDto serialize(final Message message) {
    try {
      return this.objectMapper.readValue(message.getBody(), MessageDto.class);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  private void sendToClient(final SseEmitter emitter, final Long id, final Object data) {
    try {
      log.info("ddddddd");
      emitter.send(SseEmitter.event()
          .id(id.toString())
          .name("addComment")
          .data(data));
    } catch (IOException e) {
      emitterRepository.deleteByUserId(id);
      log.error("SSE 연결이 올바르지 않습니다. 해당 memberID={}", id);
    }
  }
}
