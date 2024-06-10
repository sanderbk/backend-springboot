package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.enumeration.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * TransactionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-09T17:51:22.954Z[GMT]")


public class TransactionDTO {
  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("transactionType")
  private TransactionType transactionType = null;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @JsonProperty("timestamp")
  private LocalDateTime timestamp = null;

  @JsonProperty("from")
  private String from = null;

  @JsonProperty("to")
  private String to = null;

  @JsonProperty("amount")
  private Double amount = null;

  @JsonProperty("userPerforming")
  private UUID userPerforming = null;

  @JsonProperty("pincode")
  private Integer pincode = null;

  public TransactionDTO id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", description = "")

  @Valid
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public TransactionDTO transactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  /**
   * Get transactionType
   * @return transactionType
   **/
  @Schema(description = "")

  @Valid
  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public TransactionDTO timestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
   **/
  @Schema(description = "")

  @Valid
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public TransactionDTO from(String from) {
    this.from = from;
    return this;
  }

  /**
   * Get from
   * @return from
   **/
  @Schema(example = "NLxxINHO0xxxxxxxxx", required = true, description = "")
  @NotNull

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public TransactionDTO to(String to) {
    this.to = to;
    return this;
  }

  /**
   * Get to
   * @return to
   **/
  @Schema(example = "NLxxINHO0xxxxxxxxx", required = true, description = "")
  @NotNull

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public TransactionDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Amount of Euros
   * @return amount
   **/
  @Schema(example = "3.2", required = true, description = "Amount of Euros")
  @NotNull

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public TransactionDTO userPerforming(UUID userPerforming) {
    this.userPerforming = userPerforming;
    return this;
  }

  /**
   * Get userPerforming
   * @return userPerforming
   **/
  @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", description = "")

  @Valid
  public UUID getUserPerforming() {
    return userPerforming;
  }

  public void setUserPerforming(UUID userPerforming) {
    this.userPerforming = userPerforming;
  }

  public TransactionDTO pincode(Integer pincode) {
    this.pincode = pincode;
    return this;
  }

  /**
   * Get pincode
   * @return pincode
   **/
  @Schema(example = "1234", description = "")

  public Integer getPincode() {
    return pincode;
  }

  public void setPincode(Integer pincode) {
    this.pincode = pincode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionDTO transactionDTO = (TransactionDTO) o;
    return Objects.equals(this.id, transactionDTO.id) &&
            Objects.equals(this.transactionType, transactionDTO.transactionType) &&
            Objects.equals(this.timestamp, transactionDTO.timestamp) &&
            Objects.equals(this.from, transactionDTO.from) &&
            Objects.equals(this.to, transactionDTO.to) &&
            Objects.equals(this.amount, transactionDTO.amount) &&
            Objects.equals(this.userPerforming, transactionDTO.userPerforming) &&
            Objects.equals(this.pincode, transactionDTO.pincode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, transactionType, timestamp, from, to, amount, userPerforming, pincode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionDTO {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    userPerforming: ").append(toIndentedString(userPerforming)).append("\n");
    sb.append("    pincode: ").append(toIndentedString(pincode)).append("\n");
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
