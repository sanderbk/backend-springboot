package io.swagger.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements GrantedAuthority {
  ROLE_EMPLOYEE,
  ROLE_CUSTOMER;

  @Override
  public String getAuthority() {
    return name();
  }
}
