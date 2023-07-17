package com.css.demo.model.user;

import lombok.Data;

@Data
public class JwtSubject {
  private String sub;
  private String name;
  private String role;
  private String accountId;
}
