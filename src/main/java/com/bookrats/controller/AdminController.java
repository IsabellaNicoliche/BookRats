package com.bookrats.controller;

import com.bookrats.dto.AdminUserUpdateForm;
import com.bookrats.model.Role;
import com.bookrats.security.CustomUserDetails;
import com.bookrats.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Painel administrativo: unica area que pode listar usuarios e alterar
 * perfis de acesso (promover/revogar MODERADOR/ADMIN, ativar/desativar
 * conta). Protegida tanto pela rota "/admin/**" (SecurityConfig) quanto
 * por @PreAuthorize nos metodos de UserService chamados aqui - defesa
 * em profundidade.
 */
@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        model.addAttribute("usuario", principal.getDomainUser());
        model.addAttribute("usuarios", userService.listarTodos());
        model.addAttribute("perfisDisponiveis", Role.values());
        return "admin/dashboard";
    }

    @PostMapping("/admin/usuarios/atualizar")
    public String atualizarUsuario(@ModelAttribute AdminUserUpdateForm form,
                                    RedirectAttributes redirectAttributes) {
        try {
            userService.atualizarPerfilDeAcesso(form);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuario atualizado com sucesso.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}
