package com.example.springssestudy.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MemoDto {

  private Long id;
  private String title;
  private String content;
}
