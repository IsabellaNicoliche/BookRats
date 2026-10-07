package com.bookrats.config;

import com.bookrats.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Garante que exista pelo menos um usuario ADMIN quando a aplicacao
 * sobe pela primeira vez - sem isso, ninguem conseguiria acessar o
 * painel /admin/** para promover outros usuarios.
 *
 * <p>As credenciais vem de variaveis de ambiente (veja README). Troque
 * a senha padrao assim que fizer o primeiro login.</p>
 */
@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserService userService;
    private final String adminUsername;
    private final String adminEmail;
    private final String adminPassword;

    public DataInitializer(UserService userService,
                            @Value("${app.admin.username}") String adminUsername,
                            @Value("${app.admin.email}") String adminEmail,
                            @Value("${app.admin.password}") String adminPassword) {
        this.userService = userService;
        this.adminUsername = adminUsername;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userService.contarAdmins() == 0) {
            userService.criarAdminInicial(adminUsername, adminEmail, adminPassword);
            log.warn("Usuario ADMIN inicial criado (username='{}'). " +
                    "Troque a senha padrao assim que fizer o primeiro login!", adminUsername);
        }
    }
}
