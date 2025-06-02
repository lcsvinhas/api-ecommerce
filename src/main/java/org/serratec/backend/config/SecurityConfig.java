package org.serratec.backend.config;

import org.serratec.backend.security.JwtAuthenticationFilter;
import org.serratec.backend.security.JwtAuthorizationFilter;
import org.serratec.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/public/**").permitAll()
                        //.requestMatchers(HttpMethod.GET,"/funcionarios").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categorias").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.POST, "/categorias").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.PUT, "/categorias/{id}").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/{id}").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.GET, "/clientes").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.POST, "/clientes").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.PUT, "/clientes/{id}").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.DELETE, "/clientes/{id}").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.GET, "/pedidos").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.GET, "/pedidos/{id}").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.POST, "/pedidos").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.PUT, "/pedidos/{id}").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.DELETE, "/pedidos/{id}").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.GET, "/produtos").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.POST, "/produtos").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.PUT, "/produtos/{id}").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers(HttpMethod.DELETE, "/produtos/{id}").hasAnyRole("ADMIN", "USER","RH")
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions().disable());

        http.addFilterBefore(new JwtAuthenticationFilter(
                        authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil),
                UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new JwtAuthorizationFilter(
                        authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:2000"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
        return source;
    }

}
