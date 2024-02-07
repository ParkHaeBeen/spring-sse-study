package com.example.springssestudy.controller;

import com.example.springssestudy.dto.TokenResponse;
import com.example.springssestudy.dto.UserDto;
import com.example.springssestudy.security.UserDetailsImpl;
import com.example.springssestudy.security.UserDetailsServiceImpl;
import com.example.springssestudy.security.jwt.JwtUtils;
import com.example.springssestudy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtUtils jwtUtils;

  @PostMapping("/user/signup")
  public ResponseEntity signup(@RequestBody UserDto userDto) {
    System.out.println(userDto);
    userService.signup(userDto);
    return ResponseEntity.ok("ok");
  }

  @PostMapping("/user/signin")
  public ResponseEntity login(@RequestBody UserDto userDto) {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("로그인 실패");
    }

    UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
    String token = jwtUtils.createToken(userDetails.getUser());

    return ResponseEntity.ok(new TokenResponse(token));
  }

  @PostMapping("/user/logout")
  public ResponseEntity logout() {
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok("ok");
  }

}