package com.bookrats.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

/**
 * Habilita dois recursos do Spring Data MongoDB usados pelo projeto:
 *
 * <ul>
 *   <li>{@code @EnableMongoAuditing}: preenche automaticamente campos
 *       anotados com {@code @CreatedDate} (ex.: {@code User.createdAt}).</li>
 *   <li>{@code @EnableMongoHttpSession}: faz o Spring Session persistir
 *       as sessoes HTTP no MongoDB Atlas (colecao configurada em
 *       {@code spring.session.mongodb.collection-name}), atendendo ao
 *       requisito de armazenar tambem as sessoes no banco.</li>
 * </ul>
 */
@Configuration
@EnableMongoAuditing
@EnableMongoHttpSession
public class MongoConfig {
}
