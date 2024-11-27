package org.example.ui;

import lombok.RequiredArgsConstructor;
import org.example.services.BankAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("banking/accounts")
@RequiredArgsConstructor
public class BankAccountUI {

    private final BankAccountService service;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("accountResult", service.getAll());
        return "index";
    }

    @GetMapping("/detail/{id}")
    public String getById(
            @PathVariable String id,
            Model model
    ){
        model.addAttribute("account", service.getById(id));
        return "detail";
    }

    @ModelAttribute
    void type(Model model) {
        model.addAttribute("tab", "accounts");
    }
}
