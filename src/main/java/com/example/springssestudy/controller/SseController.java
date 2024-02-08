package com.example.springssestudy.controller;

import com.example.springssestudy.security.jwt.JwtUtils;
import com.example.springssestudy.service.NotificationService;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@RequiredArgsConstructor
@Slf4j
@RestController
public class SseController {

  private final NotificationService notificationService;

  @CrossOrigin
  @GetMapping(value = "/sub", consumes = MediaType.ALL_VALUE)
  public SseEmitter subscribe(@RequestParam String token) {
    SseEmitter emitter = notificationService.save(token);
    return emitter;
  }
}
