package com.bookrats.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {

    @Test
    void authorityTemHandlePrefixoRole() {
        assertEquals("ROLE_LEITOR", Role.LEITOR.getAuthority());
        assertEquals("ROLE_MODERADOR", Role.MODERADOR.getAuthority());
        assertEquals("ROLE_ADMIN", Role.ADMIN.getAuthority());
    }

    @Test
    void existemNoMinimoTresPerfis() {
        assertEquals(3, Role.values().length, "A atividade exige no minimo 3 perfis de usuario.");
    }
}
