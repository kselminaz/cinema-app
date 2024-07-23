package group.aist.cinemaapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        //configurations
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        //authorizes
      /*  http.authorizeHttpRequests(auth -> auth.requestMatchers("v1/user/sign-in").permitAll());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("v1/user/register").permitAll());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("v1/user/confirm-mail").permitAll());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("v1/user/forgot-password").permitAll());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("v1/user/change-password").permitAll());
*/
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

       http.oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer
                        .jwt());


        return http.build();

    }
}
