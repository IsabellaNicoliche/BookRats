package com.bookrats.controller;

import com.bookrats.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Area restrita a MODERADOR e ADMIN (veja a regra de rota
 * "/moderador/**" em SecurityConfig).
 */
@Controller
public class ModeradorController {

    @GetMapping("/moderador/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        model.addAttribute("usuario", principal.getDomainUser());
        return "moderador/dashboard";
    }
}
