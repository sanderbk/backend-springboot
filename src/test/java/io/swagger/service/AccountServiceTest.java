package io.swagger.service;

import io.swagger.model.dto.AccountDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.AccountType;
import io.swagger.model.enumeration.UserType;
import io.swagger.repo.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;




class AccountServiceTest {

    @Mock
    private AccountRepo accountRepo;

    @Autowired
    @InjectMocks
    private AccountServiceTest accountService;
    private Account account;

    List<Account> accounts = new ArrayList<>();
    private ArrayList<UserType> roles = new ArrayList<>();
    AccountDTO accountDTO;

    private User testUser;
    private Account testAccount;
    private String testIban1 = "TESTIBAN001";
    private String testIban2 = "TESTIBAN002";

    @BeforeEach
    public void setup() {

        roles.add(UserType.ROLE_CUSTOMER);
        //init wim and his account
        User ali = new User();
        ali.setActive(true);
        ali.setUsername("alistein");
        ali.setPassword("welkom");
        ali.setEmail("ali@ali.nl");
        ali.setPhone("+316 1212345678");
        ali.setDayLimit(0.00);
        ali.setUserTypes(List.of(UserType.ROLE_CUSTOMER));


        testUser = ali;

        Account aliAccount = new Account();
        aliAccount.setUser(ali);
        aliAccount.setAbsLimit(-100.00);
        aliAccount.setBalance(300.00);
        aliAccount.setIban("NL01INHO0400000001");
        aliAccount.setAccountType(AccountType.CURRENT);

        testAccount = aliAccount;

        accounts.add(testAccount);

    }

}

