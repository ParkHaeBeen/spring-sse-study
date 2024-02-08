package com.example.springssestudy.repository;

import com.example.springssestudy.domain.Notice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  List <Notice> findByInfoId(Long infoId);
}
