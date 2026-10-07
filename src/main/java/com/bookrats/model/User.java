package com.bookrats.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Usuario do sistema, persistido na colecao "users" do MongoDB Atlas.
 *
 * <p>Esta classe e intencionalmente generica (sem nenhum campo especifico
 * do dominio "livros") para poder ser reaproveitada em outro projeto no
 * futuro, trocando apenas os valores de {@link Role}.</p>
 */
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    /** Sempre armazenada em hash (BCrypt) - nunca em texto puro. */
    private String password;

    private String fullName;

    private Set<Role> roles = new HashSet<>();

    /** Permite desabilitar uma conta sem precisar excluir o usuario. */
    private boolean enabled = true;

    @CreatedDate
    private Instant createdAt;

    public User() {
    }

    public User(String username, String email, String password, String fullName, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }
}
