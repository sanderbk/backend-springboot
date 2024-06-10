package io.swagger.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;


class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    void Setup() {
        transaction = new Transaction();
    }

    @Test
    void newTransactionShouldNotBeNull() {
        Assertions.assertNotNull(transaction);
    }

    @Test
    void setAmountExpectCorrectAmountBack() {
        Double amount = 11.11;
        transaction.setAmount(amount);
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    void setAmountToPositiveNumberShouldSetThatAmount() {
        Double newAmount = new Random().nextDouble();
        transaction.setAmount(newAmount);
        assertEquals(newAmount, transaction.getAmount());
    }

    @Test
    void setAmountToNegativeShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> transaction.setAmount(-1.0));
    }

    @Test
    void setAmountToNullShouldThrowException() {
        assertThrows(NullPointerException.class, () -> transaction.setAmount(null));
    }
}
