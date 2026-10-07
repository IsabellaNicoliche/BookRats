package com.bookrats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Ponto de entrada da aplicacao BookRats.
 *
 * <p>Este projeto foi desenhado para ser GENERICO: toda a parte de
 * autenticacao/autorizacao (pacotes {@code security}, {@code config} e
 * o modelo {@code User}/{@code Role}) nao depende do dominio "livros".
 * Para reaproveitar este login em outro projeto (por exemplo, o PFC),
 * basta: (1) trocar os valores do enum {@link com.bookrats.model.Role},
 * (2) ajustar as regras de autorizacao em {@code SecurityConfig} para
 * os novos nomes de perfil e (3) trocar o tema visual via
 * {@code application.properties} (prefixo {@code app.theme.*}), sem
 * precisar tocar na logica central de login.</p>
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class BookRatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookRatsApplication.class, args);
    }
}
