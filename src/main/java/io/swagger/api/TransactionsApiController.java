package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-23T13:04:25.984Z[GMT]")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = {"Employee", "Customer"})
public class TransactionsApiController implements TransactionsApi {

    @Autowired
    private TransactionService transService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    private ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.modelMapper = new ModelMapper();
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO body) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            Transaction trans = modelMapper.map(body, Transaction.class);
            trans.setId(UUID.randomUUID());
            trans.setFrom(accountService.findAccountByIban(body.getFrom()).orElseThrow());
            trans.setUserPerforming(user.getId()); // ✅ FIX: set the user performing the transaction

            switch (trans.getTransactionType()) {
                case REGULAR:
                    trans = transService.createTransaction(trans);
                    break;
                case WITHDRAW:
                    trans = transService.createWithdrawal(trans);
                    break;
                case DEPOSIT:
                    trans = transService.createDeposit(trans);
                    break;
            }

            TransactionDTO response = modelMapper.map(trans, TransactionDTO.class);
            response.setFrom(body.getFrom());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<TransactionDTO> getTransaction(@Parameter(in = ParameterIn.PATH, description = "Transaction ID input", required = true, schema = @Schema()) @PathVariable("id") UUID id) {
        try {
            Transaction trans = transService.findTransactionById(id);
            TransactionDTO response = modelMapper.map(trans, TransactionDTO.class);
            return new ResponseEntity<TransactionDTO>(response, HttpStatus.OK);

        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No transactions found with given ID");
        }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    @GetMapping("users/me/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        UUID userId = user.getId();

        List<Transaction> transactions = transService.findTransactionsByUserId(userId);

        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactionDTOs);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    @GetMapping("/transactions/user/filter")
    public ResponseEntity<List<TransactionDTO>> getFilteredTransactions(
            @RequestParam(value = "fromAccount", required = false) String fromAccount,
            @RequestParam(value = "toAccount", required = false) String toAccount,
            @RequestParam(value = "amount", required = false) Double amount,
            @RequestParam(value = "amountFilterType", required = false) String amountFilterType,
            @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        UUID userId = user.getId();

        List<Transaction> transactions = transService.findFilteredTransactions(userId, fromAccount, toAccount, amount, amountFilterType, startTime, endTime);

        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactionDTOs);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/transactions/employee")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsForEmployees(@RequestParam("userId") UUID userId) {
        List<Transaction> transactions = transService.getAllTransactionsForEmployees(userId);

        if (transactions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No transactions found for the given user ID");
        }

        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactionDTOs);
    }
}