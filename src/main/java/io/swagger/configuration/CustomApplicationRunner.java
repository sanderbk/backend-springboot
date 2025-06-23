package io.swagger.configuration;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.AccountType;
import io.swagger.model.enumeration.UserType;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomApplicationRunner implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        User bank = new User();
        bank.setActive(true);
        bank.setUsername("InhollandBank");
        bank.setPassword("welkom");
        bank.setEmail("info@inhollandbank.nl");
        bank.setPhone("+316 012345678");
        bank.setDayLimit(0.00);
        bank.setTransLimit(Double.MAX_VALUE);
        bank.setUserTypes(List.of(UserType.ROLE_EMPLOYEE));

        double max = 1000000000000.00;
        Account bankAccount = new Account();
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
        wim.setFirstname("Wim");
        wim.setLastname("Wiltenburg");
        wim.setEmail("wim@wim.nl");
        wim.setPhone("+316 512345678");
        wim.setDayLimit(1000.00);
        wim.setTransLimit(200.00);
        wim.setUserTypes(List.of(UserType.ROLE_CUSTOMER));

        Account wimsAccount = new Account();
        wimsAccount.setAbsLimit(-100.00);
        wimsAccount.setBalance(300.00);
        wimsAccount.setIban("NL01INHO0200000001");
        wimsAccount.setAccountType(AccountType.SAVINGS);
        wimsAccount.setPincode(1234);
        wimsAccount.setActive(true);

        User frank = new User();
        frank.setActive(true);
        frank.setUsername("frankenstein");
        frank.setFirstname("Frank");
        frank.setLastname("Dersjant");
        frank.setPassword("welkom");
        frank.setEmail("frank@frank.nl");
        frank.setPhone("+316 112345678");
        frank.setDayLimit(1000.00);
        frank.setTransLimit(200.00);
        frank.setUserTypes(List.of(UserType.ROLE_CUSTOMER));

        // Fixed: Set correct user for Frank's account
        Account franksAccount = new Account();
        franksAccount.setPincode(1234);
        franksAccount.setAbsLimit(-100.00);
        franksAccount.setBalance(300.00);
        franksAccount.setIban("NL01INHO0000400001");
        franksAccount.setActive(true);
        franksAccount.setAccountType(AccountType.CURRENT);

        Account franksAccount2 = new Account();
        franksAccount2.setPincode(1234);
        franksAccount2.setAbsLimit(-100.00);
        franksAccount2.setBalance(1000.00);
        franksAccount2.setIban("NL89INHO5540881226");
        franksAccount2.setActive(true);
        franksAccount2.setAccountType(AccountType.SAVINGS);

        // Fixed: Set correct user for Wim's second account
        Account wimsAccount2 = new Account();
        wimsAccount2.setPincode(1234);
        wimsAccount2.setAbsLimit(-100.00);
        wimsAccount2.setBalance(300.00);
        wimsAccount2.setIban("NL81INHO4140881226");
        wimsAccount2.setActive(true);
        wimsAccount2.setAccountType(AccountType.CURRENT);

        User sander = new User();
        sander.setActive(true);
        sander.setUsername("sander");
        sander.setPassword("welkom");
        sander.setFirstname("Sander");
        sander.setLastname("De Jonge");
        sander.setEmail("sander@inhollandbank.nl");
        sander.setPhone("+316 112345678");
        sander.setDayLimit(10000.00);
        sander.setTransLimit(2000.00);
        sander.setUserTypes(List.of(UserType.ROLE_EMPLOYEE));

        Account sandersAccount = new Account();
        sandersAccount.setPincode(1234);
        sandersAccount.setAbsLimit(-100.00);
        sandersAccount.setBalance(300.00);
        sandersAccount.setIban("NL12INHO1140881226");
        sandersAccount.setActive(true);
        sandersAccount.setAccountType(AccountType.SAVINGS);

        Account sandersAccount2 = new Account();
        sandersAccount2.setPincode(1234);
        sandersAccount2.setAbsLimit(-100.00);
        sandersAccount2.setBalance(300.00);
        sandersAccount2.setIban("NL88INHO8840881226");
        sandersAccount2.setActive(true);
        sandersAccount2.setAccountType(AccountType.CURRENT);

        // Save users first
        User savedBank = userService.addUser(bank);
        User savedSander = userService.addUser(sander);
        User savedWim = userService.addUser(wim);
        User savedFrank = userService.addUser(frank);

        // Now set the saved (managed) users to accounts
        bankAccount.setUser(savedBank);
        wimsAccount.setUser(savedWim);
        wimsAccount2.setUser(savedWim);
        franksAccount.setUser(savedFrank);
        franksAccount2.setUser(savedFrank);
        sandersAccount.setUser(savedSander);
        sandersAccount2.setUser(savedSander);

        // Save accounts
        accountService.addAccount(bankAccount);
        accountService.addAccount(wimsAccount);
        accountService.addAccount(wimsAccount2);
        accountService.addAccount(franksAccount);
        accountService.addAccount(franksAccount2);
        accountService.addAccount(sandersAccount);
        accountService.addAccount(sandersAccount2);

        // Get managed accounts for transactions
        Account managedWimsAccount2 = accountService.findAccountByIban("NL81INHO4140881226").orElseThrow();
        Account managedFranksAccount = accountService.findAccountByIban("NL01INHO0000400001").orElseThrow();
        Account managedSanderAccount2 = accountService.findAccountByIban("NL88INHO8840881226").orElseThrow();

        // Create transactions
        Transaction t1 = new Transaction();
        t1.setFrom(managedWimsAccount2);
        t1.setTo(managedFranksAccount.getIban());
        t1.setAmount(50.00);
        t1.setPinCode(1234);
        t1.setUserPerforming(savedWim.getId());

        Transaction t2 = new Transaction();
        t2.setFrom(managedFranksAccount);
        t2.setTo(managedSanderAccount2.getIban());
        t2.setAmount(100.00);
        t2.setPinCode(1234);
        t2.setUserPerforming(savedFrank.getId());

        transactionService.createTransaction(t1);
        transactionService.createTransaction(t2);
    }
}