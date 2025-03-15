package edu.rims.medi_track.config;

import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                request -> request
                        .requestMatchers("/style/**", "/script/**", "/images/**").permitAll()
                        .requestMatchers("/file/**", "/user/**", "/").permitAll()
                        .requestMatchers("/doctor/**").hasRole(UserRole.DOCTOR.toString())
                        .requestMatchers("/client/**").hasRole(UserRole.CLIENT.toString())
                        .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.toString())
                        .anyRequest().authenticated());
        http.formLogin(form -> form.loginPage("/user/login")
                .successForwardUrl("/login/success"));
        http.logout(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    UserDetailsService detailsService() {
        return (username) -> {
            var user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(username)
                    .password(user.getPassword())
                    .roles(user.getUserRole().toString())
                    .build();
        };
    }

}