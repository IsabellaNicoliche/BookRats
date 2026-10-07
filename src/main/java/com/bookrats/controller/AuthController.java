package com.bookrats.controller;

import com.bookrats.dto.RegisterForm;
import com.bookrats.service.UserService;
import com.bookrats.service.exception.UsuarioJaExisteException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Telas publicas de autenticacao: login (a validacao do login em si e
 * feita pelo Spring Security, configurado em SecurityConfig) e cadastro.
 */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm form,
                            BindingResult bindingResult,
                            Model model) {

        if (!form.passwordsMatch()) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "As senhas nao coincidem.");
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.registrarNovoLeitor(form);
        } catch (UsuarioJaExisteException ex) {
            model.addAttribute("erroCadastro", ex.getMessage());
            return "auth/register";
        }

        return "redirect:/login?cadastroOk";
    }
}
