package com.banking.bankingmicroservicetask.controller;

import com.banking.bankingmicroservicetask.service.TransactionService;
import com.banking.dto.TransactionLimitDto;
import com.banking.bankingmicroservicetask.service.TransactionLimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Transaction Limit API", description = "Operations related to transaction limit.")
public class TransactionLimitController {

    private final TransactionLimitService transactionLimitService;

    @Operation(summary = "Save a limit",
            description = "Save a new limit into database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction limit created successfully.")
    })
    @PostMapping("/limits")
    public void saveLimit(@RequestBody TransactionLimitDto limitDto) {
        transactionLimitService.saveLimit(limitDto);
    }

    @Operation(summary = "Get a transaction limit",
            description = "Get an existing transaction limit by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Limit retrieved successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionLimitDto.class))),
            @ApiResponse(responseCode = "404", description = "Transaction limit is not found.")
    })
    @GetMapping("/limit/{id}")
    public TransactionLimitDto getLimit(@Parameter(
            description = "ID of the transaction limit to be retrieved.",
            required = true)
                                        @PathVariable UUID id) {
        return transactionLimitService.getLimit(id);
    }

    @Operation(summary = "Update a limit",
            description = "Update an existing limit.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Limit updated successfully."),
            @ApiResponse(responseCode = "404", description = "Transaction limit is not found."),
            @ApiResponse(responseCode = "403", description = "Transaction limit cannot be updated."),
            @ApiResponse(responseCode = "409", description = "Transaction limit update frequency is exceeded.")
    })
    @PutMapping("/limits/{id}")
    public void updateLimit(@Parameter(
            description = "ID of the transaction limit to be updated.",
            required = true)
                            @PathVariable UUID id, @RequestBody TransactionLimitDto transactionLimitDto) {
        transactionLimitService.updateLimit(id, transactionLimitDto);
    }

    @Operation(summary = "Delete a limit",
            description = "Delete an existing limit by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction limit deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Transaction limit not found.")
    })
    @DeleteMapping("/limit/{id}")
    public void deleteLimit(@Parameter(
            description = "ID of the transaction limit to be deleted.",
            required = true)
                            @PathVariable UUID id) {
        transactionLimitService.deleteLimit(id);
    }

}
