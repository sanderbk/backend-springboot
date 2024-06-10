package io.swagger.model.entity;

import io.swagger.model.enumeration.TransactionType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime timestamp;

    private TransactionType transactionType;


    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    private Account from;

    public void setFrom(Account from) {
        this.from = from;
    }

    private String to;

    private Double amount;

    private UUID userPerforming;

    private Integer pinCode;

    public void setAmount(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }
}
