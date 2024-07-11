package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.api.mapper.AccountMapper;
import io.swagger.api.request.SearchAccountRequest;
import io.swagger.model.dto.AccountDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import io.swagger.repo.AccountRepo;
import io.swagger.service.AccountIbanService;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-01T10:34:07.804Z[GMT]")
@RestController
@SecurityRequirement(name = "javainuseapi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = {"Employee", "Customer"})
public class AccountsApiController implements AccountsApi {
    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private ModelMapper modelMapper;
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountIbanService accountIbanService;
    @Autowired
    private AccountRepo accountRepo;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.modelMapper = new ModelMapper();
        this.accountMapper = new AccountMapper();
        this.request = request;
    }

    private boolean hasRole(Authentication authentication, String roleName) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + roleName));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<AccountDTO> addAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New account object", required = true, schema = @Schema()) @Valid @RequestBody AccountDTO body) {
        Account account = modelMapper.map(body, Account.class);

        User user = userService.findById(body.getOwnerId());
        account.setUser(user);

        AccountDTO resp = accountMapper.mapToDTO(accountService.addAccount(account));
        return new ResponseEntity<AccountDTO>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<List<AccountDTO>> getAccountsByUserId(@Parameter(in = ParameterIn.PATH, description = "User ID input", required = true, schema = @Schema()) @PathVariable("userID") UUID userID) {
        List<Account> accountList = accountService.findAccountsByUserId(userID);
        List<AccountDTO> responseDto = accountMapper.mapAccountListToDTOList(accountList);

        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<AccountDTO> getAccountByIban(@PathVariable("iban") String iban) {
        Optional<Account> optionalAccount = accountService.findAccountByIban(iban);
        Account foundAccount = optionalAccount.stream().findFirst().get();

        AccountDTO response = accountMapper.mapToDTO(foundAccount);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<Page<AccountDTO>> searchAccounts(
            SearchAccountRequest searchAccountRequest
    ) {
        Page<Account> accountList = accountService.getAllFiltered(searchAccountRequest);
        Page<AccountDTO> dtos = accountMapper.mapAccountListToDTOList(accountList);

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable("iban") String iban, @Valid @RequestBody AccountDTO body) {
        Account updatedAccount = modelMapper.map(body, Account.class);
        Account account = accountService.updateAccount(updatedAccount, iban);

        AccountDTO response = accountMapper.mapToDTO(account);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
