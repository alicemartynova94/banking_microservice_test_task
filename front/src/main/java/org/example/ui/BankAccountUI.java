package org.example.ui;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.services.BankAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("banking/accounts")
@RequiredArgsConstructor
public class BankAccountUI {

    private final BankAccountService service;

    @GetMapping
    public Mono<String> getAll(Model model) {
        return service.getAll()
            .collectList()
            .map(accounts -> {
                model.addAttribute("accountResult", accounts);
                return "index";
            });
    }

    @GetMapping("/detail/{id}")
    public Mono<String> getById(@PathVariable String id, Model model) {
        return service.getAccount(UUID.fromString(id))
            .map(account -> {
                model.addAttribute("account", account);
                return "detail";
            })
            .defaultIfEmpty("error"); // Возвращаем "error" при отсутствии данных
    }

    @ModelAttribute
    void type(Model model) {
        model.addAttribute("tab", "accounts");
    }
}
