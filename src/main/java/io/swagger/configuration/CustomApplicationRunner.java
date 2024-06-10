package io.swagger.configuration;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.AccountType;
import io.swagger.model.enumeration.UserType;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

@Configuration
public class CustomApplicationRunner implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //Add a standard User and Account for the bank itself to the DB when the application starts running

        User bank = new User();
        bank.setActive(true);
        bank.setUsername("InhollandBank");
        bank.setPassword("welkom");
        bank.setEmail("info@inhollandbank.nl");
        bank.setPhone("+316 012345678");
        bank.setDayLimit(0.00);
        bank.setUserTypes(List.of(UserType.ROLE_EMPLOYEE));

        double max = 1000000000000.00;
        Account bankAccount = new Account();
        bankAccount.setUser(bank);
        bankAccount.setAbsLimit(0.00 - Double.MAX_VALUE);
        bankAccount.setBalance(max);
        bankAccount.setIban("NL01INHO0000000001");
        bankAccount.setAccountType(AccountType.CURRENT);
        bankAccount.setActive(true);


        //init wim and his account
        User wim = new User();
        wim.setActive(true);
        wim.setUsername("wimmelstein");
        wim.setPassword("welkom");
        wim.setEmail("wim@wim.nl");
        wim.setPhone("+316 512345678");
        wim.setDayLimit(1000.00);
        wim.setUserTypes(List.of(UserType.ROLE_CUSTOMER));

        Account wimsAccount = new Account();
        wimsAccount.setUser(wim);
        wimsAccount.setAbsLimit(-100.00);
        wimsAccount.setBalance(300.00);
        wimsAccount.setIban("NL01INHO0200000001");
        wimsAccount.setAccountType(AccountType.CURRENT);
        wimsAccount.setPincode(1234);

        User frank = new User();
        String frankid = "c18044a9-0b40-42e6-b079-cac2543602a7";
        frank.setId(UUID.fromString(frankid));
        frank.setActive(true);
        frank.setUsername("frankenstein");
        frank.setPassword("welkom");
        frank.setEmail("frank@frank.nl");
        frank.setPhone("+316 112345678");
        frank.setDayLimit(1000.00);
        frank.setUserTypes(List.of(UserType.ROLE_EMPLOYEE));

        Account franksAccount = new Account();
        franksAccount.setPincode(1234);
        franksAccount.setUser(wim);
        franksAccount.setAbsLimit(-100.00);
        franksAccount.setBalance(300.00);
        franksAccount.setIban("NL01INHO0000400001");
        franksAccount.setAccountType(AccountType.CURRENT);



        userService.addUser(wim);
        userService.addUser(frank);
        userService.addUser(bank);
        accountService.addAccount(franksAccount);
        accountService.addAccount(wimsAccount);
        accountService.addAccount(bankAccount);

    }
}
