package com.bwlx.jspring.v1.service;

import com.bwlx.jspring.v1.dao.AccountDao;

public class PetStoreService {
  AccountDao account;

  public AccountDao getAccount() {
    return account;
  }

  public void setAccount(AccountDao account) {
    this.account = account;
  }
}
