package com.gokart.gokartservice.config;

public enum SecurityRoles {
  ADMINISTRATOR, //
  WORKER, //
  CLIENT, //
  ;

  public String getRoleWithPrefix() {
    return "ROLE_" + this.name();
  }
}
