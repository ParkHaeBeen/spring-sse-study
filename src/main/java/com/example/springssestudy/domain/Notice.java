package com.example.springssestudy.domain;

import com.example.springssestudy.domain.type.NoticeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "notice_id")
  private Long id;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isRead = false;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private NoticeType noticeType = NoticeType.MEMO;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_id")
  private User user;

  private Long infoId;

  public void changeIsRead(){
    isRead = true;
  }
}
