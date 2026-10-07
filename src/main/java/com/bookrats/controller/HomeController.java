package com.bookrats.controller;

import com.bookrats.model.Role;
import com.bookrats.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Redireciona o usuario autenticado para o dashboard do seu perfil
 * de maior privilegio. Mantido fora dos controllers de cada area para
 * nao misturar a logica de "para onde ir" com a logica de cada painel.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal CustomUserDetails principal) {
        var user = principal.getDomainUser();
        if (user.hasRole(Role.ADMIN)) {
            return "redirect:/admin/dashboard";
        }
        if (user.hasRole(Role.MODERADOR)) {
            return "redirect:/moderador/dashboard";
        }
        return "redirect:/leitor/dashboard";
    }

    @GetMapping("/403")
    public String acessoNegado() {
        return "error/403";
    }
}
