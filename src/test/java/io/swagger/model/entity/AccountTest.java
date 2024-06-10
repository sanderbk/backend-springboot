package io.swagger.model.entity;

import io.swagger.service.AccountIbanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {

    private Account account;
    AccountIbanService accountIbanService;


    @BeforeEach
    void setup() {
        account = new Account();
    }
  
    @Test
    void newAccountShouldNotBeNull() {
        Assertions.assertNotNull(account);

    }
    @Test
    void setUserToFrankAndExpectFrankBack() {
        User Frank = new User();
        Frank.setFirstname("Frank");
        account.setUser(Frank);
        assertEquals(account.getUser(), Frank);
    }
//    @Test
//    void setIbanToSomethingNot18Characters() {
//        assertThrows(IllegalArgumentException.class, () -> account.setIban("123456789 123456789"));
//    }
//    @Test
//    void setIbanToNull() {
//        assertThrows(NullPointerException.class, () -> account.setIban(null));
//    }
    @Test
    void setIbanToValidIbanAndExpectTheSameIbanBack() {
        String iban = "NL01INHO0000000001";
        account.setIban(iban);
        assertEquals(iban, account.getIban());
    }
    @Test
    void setPincodeWithCorrectPincode() {
        Integer pincode = 1234;
        account.setPincode(pincode);
        assertEquals(pincode, account.getPinCode());
    }
    @Test
    void setPincodeWithInCorrectPincodeBecauseItIs5Digits() {
        Integer pincode = 12345;
        assertThrows(IllegalArgumentException.class, () -> account.setPincode(pincode));
    }
    @Test
    void setActiveStatusNullAndExpectTrueActiveStatus() {
        Boolean active = null;
        account.setActive(active);
        Assertions.assertEquals(true, account.getActive());
    }

}