package com.example.springssestudy.dto;

import com.example.springssestudy.domain.type.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageDto {

  private Long id;
  private String content;
  private NoticeType noticeType;
}
