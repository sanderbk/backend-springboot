package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.enumeration.AccountType;
import io.swagger.model.enumeration.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Validated
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

  @JsonProperty("accountType")
  private AccountType accountType = null;

  @Override
  public boolean equals(Object o) {
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
            Objects.equals(this.pincode, transactionDTO.pincode) &&
            Objects.equals(this.accountType, transactionDTO.accountType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, transactionType, timestamp, from, to, amount, userPerforming, pincode, accountType);
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
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

