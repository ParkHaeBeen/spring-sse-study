package com.example.springssestudy.repository;

import com.example.springssestudy.domain.Memo;

public interface MemoDao {

  Memo getMemoDetail(Long id);
}
