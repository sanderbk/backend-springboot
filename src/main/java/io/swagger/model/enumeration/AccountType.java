package io.swagger.model.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets AccountType
 */
public enum AccountType {
  SAVINGS("savings"),
    CURRENT("current");

  private String value;

  AccountType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static AccountType fromValue(String text) {
    for (AccountType b : AccountType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
