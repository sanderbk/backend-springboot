package io.swagger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountIbanService extends AccountService {
    @Autowired
    private AccountService accountService;

    public String generateIban() {
        StringBuilder iban = new StringBuilder("NL");
        Random prefix = new Random();
        int max = 100;
        int min = 10;
        iban.append(prefix.nextInt(max - min) + min);
        iban.append("INHO");
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        iban.append(number);
        String finalIban = iban.toString();

        if (accountService.findAccountByIban(finalIban).isPresent()){
            throw new IllegalArgumentException("Something went wrong generating your iban.");
        }

        return finalIban;
    }

    public boolean isIbanPresent(String ibanGiven) {
        return ibanGiven != null;
    }
}
