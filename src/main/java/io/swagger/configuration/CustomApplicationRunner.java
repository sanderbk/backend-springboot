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
        wim.setFirstname("Wim");
        wim.setLastname("Wiltenburg");
        wim.setEmail("wim@wim.nl");
        wim.setPhone("+316 512345678");
        wim.setDayLimit(1000.00);
        wim.setUserTypes(List.of(UserType.ROLE_CUSTOMER));

        Account wimsAccount = new Account();
        wimsAccount.setUser(wim);
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
        frank.setUserTypes(List.of(UserType.ROLE_CUSTOMER));

        Account franksAccount = new Account();
        franksAccount.setPincode(1234);
        franksAccount.setUser(wim);
        franksAccount.setAbsLimit(-100.00);
        franksAccount.setBalance(300.00);
        franksAccount.setIban("NL01INHO0000400001");
        franksAccount.setActive(true);
        franksAccount.setAccountType(AccountType.CURRENT);

        Account franksAccount2 = new Account();
        franksAccount2.setPincode(1234);
        franksAccount2.setUser(frank);
        franksAccount2.setAbsLimit(-100.00);
        franksAccount2.setBalance(1000.00);
        franksAccount2.setIban("NL89INHO5540881226");
        franksAccount2.setActive(true);
        franksAccount2.setAccountType(AccountType.SAVINGS);

        Account wimsAccount2 = new Account();
        wimsAccount2.setPincode(1234);
        wimsAccount2.setUser(frank);
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
        sander.setDayLimit(1000.00);
        sander.setUserTypes(List.of(UserType.ROLE_EMPLOYEE));

        Account sandersAccount = new Account();
        sandersAccount.setPincode(1234);
        sandersAccount.setUser(sander);
        sandersAccount.setAbsLimit(-100.00);
        sandersAccount.setBalance(300.00);
        sandersAccount.setIban("NL12INHO1140881226");
        sandersAccount.setActive(true);
        sandersAccount.setAccountType(AccountType.SAVINGS);

        Account sandersAccount2 = new Account();
        sandersAccount2.setPincode(1234);
        sandersAccount2.setUser(sander);
        sandersAccount2.setAbsLimit(-100.00);
        sandersAccount2.setBalance(300.00);
        sandersAccount2.setIban("NL88INHO8840881226");
        sandersAccount2.setActive(true);
        sandersAccount2.setAccountType(AccountType.CURRENT);

        User user1 = new User();
        user1.setActive(true);
        user1.setUsername("john_doe");
        user1.setPassword("password1");
        user1.setFirstname("John");
        user1.setLastname("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPhone("+316 123456789");
        user1.setDayLimit(1500.00);
        user1.setUserTypes(List.of(UserType.ROLE_CUSTOMER));

        Account account1 = new Account();
        account1.setPincode(5678);
        account1.setUser(user1);
        account1.setAbsLimit(-50.00);
        account1.setBalance(500.00);
        account1.setIban("NL13INHO1140881227");
        account1.setActive(true);
        account1.setAccountType(AccountType.CURRENT);

        User user2 = new User();
        user2.setActive(true);
        user2.setUsername("jane_smith");
        user2.setPassword("password2");
        user2.setFirstname("Jane");
        user2.setLastname("Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setPhone("+316 987654321");
        user2.setDayLimit(2000.00);
        user2.setUserTypes(List.of(UserType.ROLE_EMPLOYEE));

        Account account2 = new Account();
        account2.setPincode(4321);
        account2.setUser(user2);
        account2.setAbsLimit(-75.00);
        account2.setBalance(250.00);
        account2.setIban("NL14INHO1140881228");
        account2.setActive(true);
        account2.setAccountType(AccountType.SAVINGS);

        User user3 = new User();
        user3.setActive(true);
        user3.setUsername("alice_wonder");
        user3.setPassword("password3");
        user3.setFirstname("Alice");
        user3.setLastname("Wonderland");
        user3.setEmail("alice.wonder@example.com");
        user3.setPhone("+316 112233445");
        user3.setDayLimit(3000.00);
        user3.setUserTypes(List.of(UserType.ROLE_CUSTOMER));

        Account account3 = new Account();
        account3.setPincode(8765);
        account3.setUser(user3);
        account3.setAbsLimit(-150.00);
        account3.setBalance(600.00);
        account3.setIban("NL15INHO1140881229");
        account3.setActive(true);
        account3.setAccountType(AccountType.CURRENT);

        User user4 = new User();
        user4.setActive(true);
        user4.setUsername("bob_builder");
        user4.setPassword("password4");
        user4.setFirstname("Bob");
        user4.setLastname("Builder");
        user4.setEmail("bob.builder@example.com");
        user4.setPhone("+316 556677889");
        user4.setDayLimit(1200.00);
        user4.setUserTypes(List.of(UserType.ROLE_CUSTOMER));

        Account account4 = new Account();
        account4.setPincode(1357);
        account4.setUser(user4);
        account4.setAbsLimit(-125.00);
        account4.setBalance(400.00);
        account4.setIban("NL16INHO1140881230");
        account4.setActive(true);
        account4.setAccountType(AccountType.SAVINGS);

        userService.addUser(sander);
        userService.addUser(wim);
        userService.addUser(frank);
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);

        userService.addUser(bank);
        accountService.addAccount(sandersAccount);
        accountService.addAccount(sandersAccount2);
        accountService.addAccount(franksAccount);
        accountService.addAccount(franksAccount2);
        accountService.addAccount(account1);
        accountService.addAccount(account2);
        accountService.addAccount(account3);
        accountService.addAccount(account4);

        accountService.addAccount(wimsAccount2);
        accountService.addAccount(wimsAccount);
        accountService.addAccount(bankAccount);

    }
}