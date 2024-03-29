package com.example.springssestudy.service;

import com.example.springssestudy.domain.Comment;
import com.example.springssestudy.domain.Memo;
import com.example.springssestudy.domain.User;
import com.example.springssestudy.dto.CommentDto;
import com.example.springssestudy.dto.MemoDto;
import com.example.springssestudy.repository.CommentRepository;
import com.example.springssestudy.repository.MemoRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemoService {

  private final MemoRepository memoRepository;
  private final CommentRepository commentRepository;

  @Transactional
  public void saveMemo(MemoDto memoDto, User user) {
    memoRepository.save(new Memo(memoDto.getTitle(), memoDto.getContent(), user));
  }

  @Transactional(readOnly = true)
  public List <MemoDto> findAllMemo() {
    return memoRepository.findAll().stream()
        .map(m -> new MemoDto(m.getId(), m.getTitle(), m.getContent())).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Memo findMemo(Long id) {
    return memoRepository.getMemoDetail(id);


  }

  @Transactional
  public Memo addComment(Long memoId, User user, CommentDto commentDto) {
    Memo memo = memoRepository.findById(memoId).get();

    Comment comment = new Comment(commentDto.getContent(), user, memo);
    memo.addComment(comment);

    return memo;
  }
}
