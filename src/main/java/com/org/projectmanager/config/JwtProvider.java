package com.org.projectmanager.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Configuration
public class JwtProvider {

  static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

  public static String generateToken(final Authentication auth) {
    return Jwts
      .builder()
      .setIssuedAt(new Date())
      .setExpiration(new Date(new Date().getTime() + 8640000))
      .claim("email", auth.getName())
      .signWith(key)
      .compact();
  }

  public String getEmailFromToken(String jwt) {
    jwt = jwt.substring(7);
    final var claims = Jwts
      .parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(jwt)
      .getBody();
    return String.valueOf(claims.get("email"));
  }
}
