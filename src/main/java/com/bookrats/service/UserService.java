package com.bookrats.service;

import com.bookrats.dto.AdminUserUpdateForm;
import com.bookrats.dto.RegisterForm;
import com.bookrats.model.Role;
import com.bookrats.model.User;
import com.bookrats.repository.UserRepository;
import com.bookrats.service.exception.UsuarioJaExisteException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Regras de negocio relacionadas a usuarios: cadastro publico,
 * consulta e administracao de perfis.
 *
 * <p>Nenhuma senha em texto puro passa por aqui sem ser imediatamente
 * transformada em hash (BCrypt) antes de ser persistida.</p>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Cadastro publico (tela de registro). Sempre cria o usuario com o
     * perfil basico {@link Role#LEITOR} - nunca permite que o proprio
     * usuario se auto-promova a MODERADOR/ADMIN.
     */
    public User registrarNovoLeitor(RegisterForm form) {
        if (!form.passwordsMatch()) {
            throw new IllegalArgumentException("As senhas informadas nao coincidem.");
        }
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new UsuarioJaExisteException("Este nome de usuario ja esta em uso.");
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new UsuarioJaExisteException("Este e-mail ja esta cadastrado.");
        }

        User user = new User();
        user.setUsername(form.getUsername().trim());
        user.setEmail(form.getEmail().trim().toLowerCase());
        user.setFullName(form.getFullName().trim());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRoles(Set.of(Role.LEITOR));
        user.setEnabled(true);

        return userRepository.save(user);
    }

    /**
     * Cria (ou garante a existencia de) um usuario administrador inicial.
     * Usado apenas pelo {@code DataInitializer} no primeiro boot.
     */
    public User criarAdminInicial(String username, String email, String rawPassword) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            User admin = new User();
            admin.setUsername(username);
            admin.setEmail(email.toLowerCase());
            admin.setFullName("Administrador BookRats");
            admin.setPassword(passwordEncoder.encode(rawPassword));
            admin.setRoles(Set.of(Role.ADMIN));
            admin.setEnabled(true);
            return userRepository.save(admin);
        });
    }

    public long contarAdmins() {
        return userRepository.countByRolesContaining(Role.ADMIN);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listarTodos() {
        return userRepository.findAllByOrderByUsernameAsc();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User atualizarPerfilDeAcesso(AdminUserUpdateForm form) {
        User user = userRepository.findById(form.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));

        if (form.getRoles() == null || form.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Selecione ao menos um perfil para o usuario.");
        }

        // Protecao simples: nao permite remover o ultimo ADMIN do sistema,
        // para o time nao ficar sem ningum com acesso administrativo.
        boolean eraAdmin = user.hasRole(Role.ADMIN);
        boolean continuaAdmin = form.getRoles().contains(Role.ADMIN);
        if (eraAdmin && !continuaAdmin && contarAdmins() <= 1) {
            throw new IllegalStateException("Nao e possivel remover o unico administrador do sistema.");
        }

        user.setRoles(form.getRoles());
        user.setEnabled(form.isEnabled());
        return userRepository.save(user);
    }
}
