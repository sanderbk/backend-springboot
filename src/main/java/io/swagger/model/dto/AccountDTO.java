package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.enumeration.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * AccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-18T15:53:51.610Z[GMT]")


public class AccountDTO   {
  @JsonProperty("iban")
  private String iban = null;

  @JsonProperty("pinCode")
  private Integer pinCode = null;


  @JsonProperty("accountType")
  private AccountType accountType = null;

  public void setPinCode(int pinCode) {
    this.pinCode = pinCode;
  }

  public int getPinCode() {
    return pinCode;
  }

  @JsonProperty("ownerId")
  private UUID ownerId = null;

  @JsonProperty("balance")
  private Double balance = null;

  @JsonProperty("absLimit")
  private Double absLimit = null;



  @JsonProperty("active")
  private Boolean active = null;

  public AccountDTO iban(String iban) {
    this.iban = iban;
    return this;
  }

  /**
   * Get iban
   * @return iban
   **/
  @Schema(example = "NLxxINHO0xxxxxxxxx", description = "")
  
    public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public AccountDTO accountType(AccountType accountType) {
    this.accountType = accountType;
    return this;
  }


  /**
   * Get accountType
   * @return accountType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public AccountDTO ownerId(UUID ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  /**
   * Get ownerId
   * @return ownerId
   **/
  @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", description = "")
  
    @Valid
    public UUID getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(UUID ownerId) {
    this.ownerId = ownerId;
  }

  public AccountDTO balance(Double balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
   **/
  @Schema(example = "3.21", required = true, description = "")
      @NotNull

    public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public AccountDTO absLimit(Double absLimit) {
    this.absLimit = absLimit;
    return this;
  }

  /**
   * Get absLimit
   * @return absLimit
   **/
  @Schema(example = "-100", required = true, description = "")
      @NotNull

    public Double getAbsLimit() {
    return absLimit;
  }

  public void setAbsLimit(Double absLimit) {
    this.absLimit = absLimit;
  }

  public AccountDTO active(Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Get active
   * @return active
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountDTO accountDTO = (AccountDTO) o;
    return Objects.equals(this.iban, accountDTO.iban) &&
        Objects.equals(this.accountType, accountDTO.accountType) &&
        Objects.equals(this.ownerId, accountDTO.ownerId) &&
        Objects.equals(this.balance, accountDTO.balance) &&
        Objects.equals(this.absLimit, accountDTO.absLimit) &&
        Objects.equals(this.active, accountDTO.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(iban, accountType, ownerId, balance, absLimit, active);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountDTO {\n");
    
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    absLimit: ").append(toIndentedString(absLimit)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
