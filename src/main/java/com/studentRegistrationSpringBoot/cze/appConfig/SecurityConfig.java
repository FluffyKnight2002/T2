package com.studentRegistrationSpringBoot.cze.appConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;



@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests( authorize -> authorize
                        /*Admin Mapping*/
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")

                        /*auth mapping*/
                        .requestMatchers("/register").permitAll()



                        .anyRequest()
                        .authenticated()
                )
                .formLogin( form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .successHandler(successHandler())
                        .permitAll()

                )
                .logout( logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutHandler logoutHandler(){
        return new CustomLogoutHandler();
    }

    public static class CustomLogoutHandler implements LogoutHandler {

        private static final Logger logger = LoggerFactory.getLogger(CustomLogoutHandler.class);

        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            // Perform custom actions during logout
            // For example, you can clear session attributes, update user status, or log additional information

            // Clear session attributes
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            // Log additional information
            String username = authentication.getName();
            logger.info("User '{}' logged out", username);
        }


    }
}
