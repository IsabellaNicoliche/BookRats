package com.bookrats.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Garante que a senha nunca e armazenada em texto puro e que o hash
 * gerado pode ser validado corretamente - o mesmo mecanismo usado em
 * {@code SecurityConfig.passwordEncoder()} e {@code UserService}.
 */
class PasswordHashingTest {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void senhaEArmazenadaComoHashDiferenteDoTextoOriginal() {
        String senhaOriginal = "MinhaSenhaForte123!";
        String hash = encoder.encode(senhaOriginal);

        assertNotEquals(senhaOriginal, hash);
        assertTrue(encoder.matches(senhaOriginal, hash));
    }

    @Test
    void senhaIncorretaNaoDeveValidar() {
        String hash = encoder.encode("SenhaCorreta123!");
        assertFalse(encoder.matches("SenhaErrada999!", hash));
    }
}
