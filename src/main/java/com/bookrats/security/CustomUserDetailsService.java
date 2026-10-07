package com.bookrats.security;

import com.bookrats.model.User;
import com.bookrats.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Carrega o usuario para o Spring Security no momento do login.
 *
 * <p>Aceita tanto o username quanto o e-mail no campo de login, o que
 * e comum em sistemas reais e nao exige nenhuma mudanca no formulario
 * caso o usuario digite um ou outro.</p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Nao foi encontrado usuario com username/e-mail: " + usernameOrEmail));

        return new CustomUserDetails(user);
    }
}
