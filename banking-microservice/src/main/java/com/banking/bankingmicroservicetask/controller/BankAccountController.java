package com.banking.bankingmicroservicetask.controller;

import com.banking.api.BankAccountApi;
import com.banking.bankingmicroservicetask.service.BankAccountService;
import com.banking.dto.BankAccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Bank Account API", description = "Operations related to bank accounts.")
public class BankAccountController implements BankAccountApi {

    private final BankAccountService bankAccountService;

    @Override
    @Operation(summary = "Get a bank account",
            description = "Get an existing bank account by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank account retrieved successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDto.class))),
            @ApiResponse(responseCode = "404", description = "Bank account limit is not found.")
    })
    @GetMapping("/{id}")
    public BankAccountDto getAccount(@Parameter(
            description = "ID of the bank account to be retrieved.",
            required = true)
                                     @PathVariable UUID id) {
        return bankAccountService.getAccount(id);
    }

    @Override
    @Operation(summary = "Get all bank accounts",
        description = "Retrieve a list of all existing bank accounts.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of bank accounts retrieved successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDto.class))),
        @ApiResponse(responseCode = "404", description = "No bank accounts found.")
    })
    @GetMapping
    public List<BankAccountDto> getAll() {
        return bankAccountService.getAll();
    }

    @Override
    @Operation(summary = "Save a bank account",
            description = "Save a new bank account into database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bank account created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDto.class)))
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BankAccountDto addNewAccount(@RequestBody BankAccountDto bankAccountDto) {
        bankAccountService.saveAccount(bankAccountDto);
        return bankAccountDto;
    }

    @Override
    @Operation(summary = "Delete a bank account",
            description = "Delete an existing bank account by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bank account deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Bank account not found.")
    })
    @DeleteMapping("/{id}")
    public void deleteAccount(@Parameter(
            description = "ID of the bank account to be deleted.",
            required = true)
            @PathVariable UUID id) {
        bankAccountService.deleteAccount(id);
    }
}

