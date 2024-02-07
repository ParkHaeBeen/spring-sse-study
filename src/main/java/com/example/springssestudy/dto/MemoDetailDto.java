package com.example.springssestudy.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MemoDetailDto {

  private Long memoId;
  private String title;
  private String content;
  private List <CommentDto> comments = new ArrayList <>();
}
