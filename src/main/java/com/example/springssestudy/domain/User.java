package com.example.springssestudy.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue
  private Long id;

  private String username;

  private String password;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
