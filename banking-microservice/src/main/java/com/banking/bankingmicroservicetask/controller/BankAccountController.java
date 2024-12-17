package com.banking.bankingmicroservicetask.controller;

import com.banking.api.BankAccountApi;
import com.banking.dto.BankAccountDto;
import com.banking.bankingmicroservicetask.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
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
    @GetMapping("/account/{id}")
    public BankAccountDto getAccount(@Parameter(
            description = "ID of the bank account to be retrieved.",
            required = true)
                                     @PathVariable UUID id) {
        return bankAccountService.getAccount(id);
    }

    @Override
    @GetMapping("/accounts")
    public List<BankAccountDto> getAllAccounts() {
        return bankAccountService.getAllAccounts();
    }

    @Override
    @Operation(summary = "Save a bank account",
            description = "Save a new bank account into database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bank account created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDto.class)))
    })
    @PostMapping("/accounts")
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
    @DeleteMapping("/account/{id}")
    public void deleteAccount(@Parameter(
            description = "ID of the bank account to be deleted.",
            required = true)
            @PathVariable UUID id) {
        bankAccountService.deleteAccount(id);
    }
}

