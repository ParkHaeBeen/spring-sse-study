package com.example.springssestudy.repository;

import com.example.springssestudy.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository <Memo, Long>, MemoDao {
}
