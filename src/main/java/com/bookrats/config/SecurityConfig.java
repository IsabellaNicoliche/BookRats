package com.bookrats.config;

import com.bookrats.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracao central de autenticacao e autorizacao.
 *
 * <p>Regras de acesso por rota (autorizacao por perfil/role):</p>
 * <ul>
 *   <li>{@code /admin/**}      -> somente ADMIN</li>
 *   <li>{@code /moderador/**}  -> MODERADOR ou ADMIN</li>
 *   <li>{@code /leitor/**}     -> qualquer usuario autenticado (LEITOR, MODERADOR ou ADMIN)</li>
 *   <li>demais rotas autenticadas por padrao; login/registro/estaticos sao publicos.</li>
 * </ul>
 *
 * <p>{@code @EnableMethodSecurity} tambem habilita as anotacoes
 * {@code @PreAuthorize} usadas em {@code UserService}, como uma segunda
 * camada de autorizacao (defesa em profundidade), independente das
 * regras de rota abaixo.</p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt: hash forte, com salt automatico - nunca armazenamos senha em texto puro.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService,
                                                           PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login", "/register", "/403",
                                "/css/**", "/js/**", "/images/**", "/webjars/**", "/error"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/moderador/**").hasAnyRole("MODERADOR", "ADMIN")
                        .requestMatchers("/leitor/**").hasAnyRole("LEITOR", "MODERADOR", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "SESSION")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/403"));

        return http.build();
    }
}
