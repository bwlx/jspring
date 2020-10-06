package com.bwlx.jspring.v1;

import com.bwlx.jspring.v1.AccountDao;

public class PetStoreService {
  AccountDao account;

  public AccountDao getAccount() {
    return account;
  }

  public void setAccount(AccountDao account) {
    this.account = account;
  }
}
