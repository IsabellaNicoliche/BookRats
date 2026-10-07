package com.bookrats.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Dados recebidos do formulario de cadastro publico.
 * Todo novo usuario cadastrado por aqui recebe automaticamente o perfil
 * LEITOR - perfis mais altos (MODERADOR/ADMIN) so podem ser atribuidos
 * por um ADMIN ja autenticado, pelo painel administrativo.
 */
public class RegisterForm {

    @NotBlank(message = "Informe um nome de usuario.")
    @Size(min = 3, max = 30, message = "O nome de usuario deve ter entre 3 e 30 caracteres.")
    private String username;

    @NotBlank(message = "Informe seu nome completo.")
    @Size(max = 120, message = "Nome completo muito longo.")
    private String fullName;

    @NotBlank(message = "Informe um e-mail.")
    @Email(message = "Informe um e-mail valido.")
    private String email;

    @NotBlank(message = "Informe uma senha.")
    @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres.")
    private String password;

    @NotBlank(message = "Confirme a senha.")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean passwordsMatch() {
        return password != null && password.equals(confirmPassword);
    }
}
