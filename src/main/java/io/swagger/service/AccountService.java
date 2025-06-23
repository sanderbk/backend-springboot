package io.swagger.service;

import io.swagger.api.exception.NotFoundException;
import io.swagger.api.request.SearchAccountRequest;
import io.swagger.model.entity.Account;
import io.swagger.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountIbanService accountIbanService;

    //account validation
    public Account addAccount(Account a) {
        validateAccount(a);

        // If IBAN is not set or belongs to a normal user, assign or generate IBAN
        accountIbanService.assignIbanIfMissing(a);

        // Prevent duplicate IBANs
        if (accountRepo.findAccountByIban(a.getIban()).isPresent()) {
            throw new IllegalArgumentException("An account with this IBAN already exists.");
        }

        return Optional.of(accountRepo.save(a))
                .orElseThrow(() -> new NoSuchElementException("Something went wrong; the server couldn't respond with new account object"));
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

        if (CollectionUtils.isEmpty(accountList)) {
            throw new NotFoundException("Account not found for user with ID: " + userId);
        }

        return accountList;
    }

    public Account updateAccount(Account updatedAccount, String iban) {
        Optional<Account> optionalAccount = accountRepo.findAccountByIban(iban).stream().findFirst();

        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found for IBAN: " + iban);
        }

        Account existingAccount = optionalAccount.get();

        // Preserve the immutable properties
        updatedAccount.setIban(existingAccount.getIban());
        updatedAccount.setUser(existingAccount.getUser());

        try {
            return accountRepo.save(updatedAccount);
        } catch (DataAccessException e) {
            throw new RuntimeException("Something went wrong trying to update your account.", e);
        }
    }

    //find an accountlist by iban input
    public Optional<Account> findAccountByIban(String iban) {
        Optional<Account> optionalAccount = accountRepo.findAccountByIban(iban).stream().findFirst();

        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found for IBAN: " + iban);
        }

        return accountRepo.findAccountByIban(iban);
    }

    public Page<Account> getAllFiltered(SearchAccountRequest searchAccountRequest) {

        var qryPage = searchAccountRequest.getPage().orElse(0);
        var qrySize = searchAccountRequest.getSize().orElse(50);
        Pageable pageAble = PageRequest.of(qryPage, qrySize);

        var qryIban = searchAccountRequest.getIban().orElse("");
        var qryFirstname = searchAccountRequest.getFirstname().orElse("");
        var qryLastname = searchAccountRequest.getLastname().orElse("");
        var qryUsername = searchAccountRequest.getUsername().orElse("");
        var qryType = searchAccountRequest.getAccountType().orElse(null);

        return accountRepo.findAccounts(qryIban, qryFirstname, qryLastname, qryUsername, qryType, pageAble);
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
