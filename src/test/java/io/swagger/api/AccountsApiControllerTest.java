package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.dto.TokenDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.AccountType;
import io.swagger.model.enumeration.UserType;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountsApiControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    List<Account> accounts;

    private User frankMock;
    private User wimMock;
    private Account frankAccountMock;
    private Account wimAccountMock;
    private Account bankAccountMock;

    private String frankUUID;

    private String jwt;

    public AccountsApiControllerTest() {

    }

    @BeforeEach
    public void setup() throws Exception {
        List<Account> accountList = new ArrayList<>();

        User frank = new User();
        String frankid = "c18044a9-0b40-42e6-b079-cac2543602a7";
        frank.setId(UUID.fromString(frankid));
        frank.setActive(true);
        frank.setUsername("frankenstein");
        frank.setPassword("welkom");
        frank.setEmail("frank@frank.nl");
        frank.setPhone("+316 112345678");
        frank.setDayLimit(0.00);
        frank.setUserTypes(List.of(UserType.ROLE_EMPLOYEE));

        Account franksAccount = new Account();
        franksAccount.setUser(frank);
        franksAccount.setAbsLimit(-100.00);
        franksAccount.setBalance(300.00);
        franksAccount.setIban("NL01INHO0000400001");
        franksAccount.setAccountType(AccountType.CURRENT);
        franksAccount.setPincode(1234);

        accountList.add(franksAccount);

        //init wim and his account
        User wim = new User();
        wim.setActive(true);
        wim.setUsername("wimmelstein");
        wim.setPassword("welkom");
        wim.setEmail("wim@wim.nl");
        wim.setPhone("+316 512345678");
        wim.setDayLimit(0.00);
        wim.setUserTypes(List.of(UserType.ROLE_CUSTOMER));

        Account wimsAccount = new Account();
        wimsAccount.setUser(wim);
        wimsAccount.setAbsLimit(-100.00);
        wimsAccount.setBalance(300.00);
        wimsAccount.setIban("NL01INHO0200000001");
        wimsAccount.setAccountType(AccountType.CURRENT);

        accountList.add(wimsAccount);

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

        accountList.add(bankAccount);

        frankAccountMock = franksAccount;
        wimAccountMock = wimsAccount;
        frankMock = frank;
        wimMock = wim;
        bankAccountMock = bankAccount;
        accounts = accountList;

        this.login();

    }

    @BeforeEach
    public void login() throws Exception {
        MvcResult res = mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"frankenstein\", \"password\": \"welkom\"}"))
                .andExpect(status().isOk()
                ).andReturn();

        String contentAsString = res.getResponse().getContentAsString();

        TokenDTO tokenDTO = objectMapper.readValue(contentAsString, TokenDTO.class);

        this.jwt = tokenDTO.getToken();
    }

    @Test
    void getAccounts() throws Exception {
        mvc.perform((get("/accounts/")
                .header("Authorization", "Bearer " + this.jwt))
        ).andExpect(status().isOk());
    }

    @Test
    void getAccountsWithoutBeingLoggedIn() throws Exception {
        mvc.perform((get("/accounts/")
        )).andExpect(status().isForbidden());
    }

    @Test
    void getAccountThatDoesNotExist() throws Exception {
        mvc.perform((get("/accounts/getByIban/thisibandoesnotexist") .header("Authorization", "Bearer " + this.jwt))
        ).andExpect(status().isNotFound());
    }

    @Test
    void addAnAccountWithExistingUserForOwnerId() throws Exception{
        mvc.perform(post("/accounts")
                        .header("Authorization", "Bearer " + this.jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"ownerId\": \"%s\", \"pinCode\": \"%s\", \"accountType\": \"%s\", \"active\": \"%s\", \"balance\": \"%s\", \"absLimit\": \"%s\"}",
                                this.userService.findByUsername("frankenstein").getId(),
                                frankAccountMock.getPinCode(),
                                frankAccountMock.getAccountType(),
                                true,
                                frankAccountMock.getBalance(),
                                frankAccountMock.getAbsLimit())))
                .andExpect(status().isCreated());
    }

    @Test
    void addAnAccountButDontFillAllTheRequiredFields() throws Exception{
        mvc.perform(post("/accounts")
                        .header("Authorization", "Bearer " + this.jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"ownerId\": \"%s\", \"pinCode\": \"%s\", \"accountType\": \"%s\", \"active\": \"%s\", \"balance\": \"%s\", \"absLimit\": \"%s\"}",frankMock.getId(), null, null, true, null, frankAccountMock.getAbsLimit())))
                .andExpect(status().isBadRequest());

    }

    @Test
    void getAccount() throws Exception {
        mvc.perform((get("/accounts/getByIban/" + bankAccountMock.getIban())
                .header("Authorization", "Bearer " + this.jwt))
        ).andExpect(status().isOk());
    }
}