/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.56).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.dto.TransactionDTO;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-07T09:26:30.313122969Z[GMT]")
@Validated
public interface TransactionsApi {

    @Operation(summary = "Create a new transaction", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employee", "Customer" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid transaction") })
    @RequestMapping(value = "/transactions",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<TransactionDTO> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "New transaction object", required=true, schema=@Schema()) @Valid @RequestBody TransactionDTO body
    );


    @Operation(summary = "Get one transaction on Id", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employee", "Customer" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),

            @ApiResponse(responseCode = "404", description = "Transaction not found") })
    @RequestMapping(value = "/transactions/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<TransactionDTO> getTransaction(@Parameter(in = ParameterIn.PATH, description = "Transaction ID input", required=true, schema=@Schema()) @PathVariable("id") UUID id
    );


    @Operation(summary = "Get all transactions by logged-in user", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employee", "Customer" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions found", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TransactionDTO.class)))),

            @ApiResponse(responseCode = "404", description = "No transactions found") })
    @RequestMapping(value = "/transactions/byUser",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<TransactionDTO>> getTransactionsByUser();

}
