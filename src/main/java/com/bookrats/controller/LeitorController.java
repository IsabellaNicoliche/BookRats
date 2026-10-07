package com.bookrats.controller;

import com.bookrats.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Area acessivel por qualquer usuario autenticado (LEITOR, MODERADOR ou
 * ADMIN - veja a regra de rota "/leitor/**" em SecurityConfig).
 */
@Controller
public class LeitorController {

    @GetMapping("/leitor/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        model.addAttribute("usuario", principal.getDomainUser());
        return "leitor/dashboard";
    }
}
