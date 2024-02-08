package com.example.springssestudy.security;

import com.example.springssestudy.domain.User;
import com.example.springssestudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUserName(username).orElseThrow(
        () -> new UsernameNotFoundException("로그인 오류")
    );

    return new UserDetailsImpl(user);
  }
}
