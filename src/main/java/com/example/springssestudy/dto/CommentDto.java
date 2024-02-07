package com.example.springssestudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentDto {
  private Long id;
  private String username;
  private String content;
}
