package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.dto.AccountDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-01T10:34:07.804Z[GMT]")
@RestController
@SecurityRequirement(name="javainuseapi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = {"Employee", "Customer"})
public class AccountsApiController implements AccountsApi {
    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private ModelMapper modelMapper;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountIbanService accountIbanService;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.modelMapper = new ModelMapper();
        this.request = request;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<AccountDTO> addAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New account object", required = true, schema = @Schema()) @Valid @RequestBody AccountDTO body) {
        try {
            Account account = modelMapper.map(body, Account.class);
            User user = userService.findById(body.getOwnerId());
            account.setUser(user);

            AccountDTO resp = modelMapper.map(accountService.addAccount(account), AccountDTO.class);
            resp.setOwnerId(user.getId());

            return new ResponseEntity<AccountDTO>(resp, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<List<AccountDTO>> getAccountsByOwnerID(@Parameter(in = ParameterIn.PATH, description = "User ID input", required = true, schema = @Schema()) @PathVariable("userID") UUID userID) {
        List<Account> accountList = accountService.findAccountsByUserId(userID);
        List<AccountDTO> responseDto = accountList
                .stream()
                .map(user -> modelMapper.map(user, AccountDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < responseDto.size(); i++) {
            responseDto.get(i).setOwnerId(accountList.get(i).getUser().getId());
        }

        return new ResponseEntity<List<AccountDTO>>(responseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<AccountDTO> getAccountByIban(@PathVariable("iban") String iban) {
        Optional<Account> optionalAccount = accountService.findAccountByIban(iban);

        if (optionalAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with the given IBAN not found.");
        }

        Account foundAccount = optionalAccount.get();
        AccountDTO response = modelMapper.map(foundAccount, AccountDTO.class);
        response.setOwnerId(foundAccount.getUser().getId());

        return ResponseEntity.ok(response);
    }

    private boolean hasRole(Authentication authentication, String roleName) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + roleName));
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<List<AccountDTO>> getAccounts(
            @Min(0) @Parameter(in = ParameterIn.QUERY, description = "Number of records to skip for pagination",
                    schema = @Schema(allowableValues = {})) @Valid @RequestParam(value = "skip", required = false) Integer skip,
            @Min(1) @Max(200000) @Parameter(in = ParameterIn.QUERY, description = "Maximum number of records to return",
                    schema = @Schema(allowableValues = {}, minimum = "1", maximum = "200000")) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        List<Account> accountList = accountService.getAll(skip, limit);

        List<AccountDTO> dtos = accountList.stream()
                .map(account -> {
                    AccountDTO dto = modelMapper.map(account, AccountDTO.class);
                    dto.setOwnerId(account.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable("iban") String iban, @Valid @RequestBody AccountDTO body) {
        Optional<Account> optionalAccount = accountService.findAccountByIban(iban);

        if (optionalAccount.isPresent()) {
            Account foundAccount = optionalAccount.get();
            // Map account from the request body
            Account account = modelMapper.map(body, Account.class);
            // Preset properties that should not be changed
            account.setIban(foundAccount.getIban());
            account.setUser(foundAccount.getUser());

            account = accountService.updateAccount(account);
            AccountDTO response = modelMapper.map(account, AccountDTO.class);
            response.setOwnerId(account.getUser().getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an account to update.");
        }
    }
}
