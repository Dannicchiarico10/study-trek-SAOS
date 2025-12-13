package vttp.proj2.backend.config;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    @Value("${jwt.key.secret}")
    private String secretKey;

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256")).build();

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                // 1. CONFIGURAZIONE CORS STRETTA: Aggiunta la sorgente di configurazione CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(auth ->
                        // Consentiti solo Login, Registrazione e la pagina iniziale (/)
                        auth.requestMatchers("/", "/api/auth/login", "/api/auth/register").permitAll()

                                // Tutti gli altri endpoint richiedono un JWT valido
                                .anyRequest().authenticated()
                )

                // 2. CONFIGURAZIONE RESOURCE SERVER: Abilita JWT Bearer token authentication
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                // 3. CONFIGURAZIONE STATELESS: Disabilita le sessioni HTTP
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // NOTA: Rimosso .userDetailsService(userDetailsSvc) e .httpBasic(Customizer.withDefaults())
                // perché non sono necessari per un Resource Server basato su JWT stateless.
                .build();
    }

    // --- BEAN PER LA CONFIGURAZIONE CORS STRETTA ---
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Specifica le ORIGINI del tuo frontend (dove si trova la tua applicazione client)
        // Devi cambiare 'http://localhost:4200' con l'URL del tuo frontend in produzione!
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://127.0.0.1:4200")); // AGGIUNGI QUI IL TUO DOMINIO DI PRODUZIONE

        // I metodi consentiti
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Gli header consentiti (Authorization è cruciale per inviare il JWT)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));

        // Consente al frontend di inviare credenziali (come i cookies, anche se non usati con JWT)
        configuration.setAllowCredentials(true);

        // Età massima della pre-flight request (buona pratica)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Applica questa configurazione a TUTTI i percorsi
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    // ------------------------------------------------

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

}