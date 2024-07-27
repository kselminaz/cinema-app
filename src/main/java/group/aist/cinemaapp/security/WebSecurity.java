package group.aist.cinemaapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private final JwtAuthConverter jwtAuthConverter;

    @Value("${keycloak.base-url}/realms/${keycloak.realm}")
    private String issuer;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        //configurations
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/api/create-user",
                                "/api/login", "/api/token",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/error").permitAll()
                        .requestMatchers(HttpMethod.GET,"/v1/movies/**","/v1/languages/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {

        return NimbusJwtDecoder.withIssuerLocation(issuer).build();
    }
}

