package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountIbanService accountIbanService;

    //account validation
    public Account addAccount(Account a) {
        validateAccount(a);

        if (!accountIbanService.isIbanPresent(a.getIban())) {
            String iban = accountIbanService.generateIban();
            a.setIban(iban);
        }

        return Optional.of(accountRepo.save(a)).orElseThrow(
                () ->  new NoSuchElementException("Something went wrong; the server couldn't respond with new account object"));
    }

    private void validateAccount(Account a) {
        if (!allFieldsFilled(a)) {
            throw new IllegalArgumentException("Please fill in all the required fields.");
        }

        if (!pinCheck(a.getPinCode())) {
            throw new IllegalArgumentException("Pin code has to be 4 digits long and of type Integer.");
        }
    }

    //find an accountlist by using the userid/owner id
    public List<Account> findAccountsByUserId(UUID userId) {
        List<Account> accountList = accountRepo.findAccountsByUserId(userId);
        if (accountList.isEmpty()) {
            throw new IllegalArgumentException("Something went wrong trying to find accounts with userid: " + userId);
        }
        return accountList;
    }

    //update an account with newly inserted account
    public Account updateAccount(Account a) {
        return Optional.of(accountRepo.save(a)).orElseThrow(
                () -> new IllegalArgumentException("Something went wrong trying to update your account."));
    }

    //find an accountlist by iban input
    public Optional<Account> findAccountByIban(String iban) {
        return accountRepo.findAccountByIban(iban);
    }

    public List<Account> getAll(Integer skip, Integer limit) {
        if (skip == null && limit == null) {
            return getAll();
        }

        if (skip == null) {
            skip = 0;
        }
        if (limit == null) {
            limit = Integer.MAX_VALUE;
        }

        Pageable pageable = PageRequest.of(skip, limit);
        return accountRepo.findAll(pageable).getContent();
    }

    public List<Account> getAll() {
        return accountRepo.findAll();
    }

    //pincode check for integer
    private boolean pinCheck(Integer pin) {
        return String.valueOf(pin).length() == 4;
    }

    //check if all required fields are filled
    private boolean allFieldsFilled(Account a) {
        return a.getBalance() != null && a.getAccountType() != null && a.getUser() != null;
    }
}
