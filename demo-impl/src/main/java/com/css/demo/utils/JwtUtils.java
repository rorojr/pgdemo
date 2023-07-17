package com.css.demo.utils;

import com.css.demo.model.user.JwtSubject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class JwtUtils {

  private static final String SECRET = "YesWeAre";
  private static final String ISS = "HI-CTO";

  public static String createToken(JwtSubject jwtSubject, Date expireTime) {
    return Jwts.builder()
        .setIssuer(ISS)
        .setSubject(JsonUtils.serialize(jwtSubject))
        .setIssuedAt(new Date())
        .setExpiration(expireTime)
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
  }

  public static JwtSubject getParsedSubject(String token) {
    Optional<JwtSubject> jwtSubject = JsonUtils.deserialize(JwtUtils.getSubject(token), JwtSubject.class);
    return jwtSubject.orElse(null);
  }

  public static String getSubject(String token) {
    Claims claims = getTokenBody(token);
    if (claims == null) {
      return "";
    }
    return claims.getSubject();
  }

  public static boolean isExpiration(String token) {
    Date date = getExpiration(token);
    if (Objects.isNull(date)) {
      return true;
    }
    return date.before(new Date());
  }

  public static Date getExpiration(String token) {
    Claims claims = getTokenBody(token);
    if (Objects.isNull(claims)) {
      return null;
    }
    return claims.getExpiration();
  }

  private static Claims getTokenBody(String token) {
    try {
      return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      log.error("getTokenBody error:", e);
    }
    return null;
  }
}
