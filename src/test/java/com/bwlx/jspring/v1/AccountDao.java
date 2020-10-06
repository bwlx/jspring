package com.bwlx.jspring.v1;

import com.bwlx.jspring.annotation.Component;
import com.bwlx.jspring.annotation.Value;

@Component
public class AccountDao {
  @Value("bwlx")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}