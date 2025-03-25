package org.ai.hospitalmanagementapplicationbackend.configuration;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

 @Bean
 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

     return http
             .csrf(AbstractHttpConfigurer::disable)
             .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .authorizeHttpRequests(authorizeRequests->authorizeRequests.anyRequest().permitAll())
             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
             .cors(cors->cors.configurationSource(corsConfigurationSource()))
             .build();




 }

    private CorsConfigurationSource corsConfigurationSource() {
     return new CorsConfigurationSource() {
         @Override
         public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
             CorsConfiguration config = new CorsConfiguration();
             config.setAllowCredentials(true);
             config.setAllowedOrigins(Arrays.asList("http://localhost:5173","http://localhost:5174"));
             config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
             config.setMaxAge(3600L);
             config.setExposedHeaders(Collections.singletonList("Authorization"));
             return config;
         }

     };
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
     return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
     return configuration.getAuthenticationManager();
    }


}
