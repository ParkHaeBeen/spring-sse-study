package com.example.springssestudy.dto;

import com.example.springssestudy.domain.Notice;
import com.example.springssestudy.domain.type.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class MessageDto {

  private Long id;
  private String content;
  private NoticeType noticeType;
  private Long userId;
}
