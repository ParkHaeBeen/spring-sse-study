package com.example.springssestudy.controller;

import com.example.springssestudy.domain.Comment;
import com.example.springssestudy.domain.Memo;
import com.example.springssestudy.dto.CommentDto;
import com.example.springssestudy.dto.MemoDto;
import com.example.springssestudy.security.UserDetailsImpl;
import com.example.springssestudy.service.MemoService;
import com.example.springssestudy.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemoController {

  private final MemoService memoService;
  private final NotificationService notificationService;

  @PostMapping("/memo")
  public ResponseEntity postMemo(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody MemoDto memoDto
  ) {
    memoService.saveMemo(memoDto, userDetails.getUser());
    return ResponseEntity.ok("ok");
  }

  @GetMapping("/memos")
  public ResponseEntity<List<MemoDto>> getAllMemos() {
    List <MemoDto> memos = memoService.findAllMemo();
    return ResponseEntity.ok(memos);
  }

  @GetMapping("/memo")
  public Memo getMemo(@RequestParam Long id) {
    log.info("getMemo id = {}", id);
    return memoService.findMemo(id);
  }

  @PostMapping("/memo/{id}/comment")
  public ResponseEntity addComment(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long id,
      @RequestBody CommentDto commentDto) {
    Comment comment = memoService.addComment(id, userDetails.getUser(), commentDto);
    notificationService.notifyAddCommentEvent(comment);
    return ResponseEntity.ok("ok");
  }
}
