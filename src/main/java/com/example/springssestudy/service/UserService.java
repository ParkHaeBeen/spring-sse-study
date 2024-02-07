package com.example.springssestudy.service;

import com.example.springssestudy.domain.User;
import com.example.springssestudy.dto.UserDto;
import com.example.springssestudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Transactional
  public void signup(UserDto userDto) {
    User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()));
    userRepository.save(user);
  }
}