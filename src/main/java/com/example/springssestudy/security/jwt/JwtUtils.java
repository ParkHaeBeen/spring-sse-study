package com.example.springssestudy.security.jwt;

import com.example.springssestudy.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
  private final String SECRET = "secret";
  private final SignatureAlgorithm SIGNATURE = SignatureAlgorithm.HS256;

  // 토큰 생성
  // Claim은 subject 역할의 username과 만료시간 두가지를 설정
  public String createToken(User user) {

    return Jwts.builder()
        .claim("username", user.getUserName())
        .claim("userId", user.getId())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(SIGNATURE, SECRET)
        .compact();
  }

  // 토큰의 Claim에서 username을 가져온다.
  public String getUsernameFromToken(String token) {
    Claims claims = getAllClaims(token);

    return String.valueOf(claims.get("username"));
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = getAllClaims(token);

    return Long.valueOf(String.valueOf(claims.get("userId")));
  }

  // 토큰 유효여부 확인
  // 현재는 토큰의 만료시간만을 검증
  public Boolean isValidToken(String token) {
    Date expiration = getAllClaims(token).getExpiration();
    return expiration.after(new Date());
  }

  private Claims getAllClaims(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET)
        .parseClaimsJws(token)
        .getBody();
  }
}