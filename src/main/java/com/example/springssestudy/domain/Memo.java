package com.example.springssestudy.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Memo {

  @Id
  @GeneratedValue
  private Long id;

  private String title;

  private String content;

  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL)
  private List <Comment> comments = new ArrayList <>();

  public Memo(String title, String content, User user) {
    this.title = title;
    this.content = content;
    this.user = user;
  }

  public void addComment(Comment comment) {
    this.getComments().add(comment);
    comment.setMemo(this);
  }
}