package com.GesW.ToDo_List.config;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;


@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /*
    We set up JWT encoding and decoding using RSA keys for secure token handling.
    We employ BCrypt for password hashing.
     */

    @Value("${jwt.private-key}")
    private RSAPrivateKey rsaPrivateKey;
    @Value("${jwt.public-key}")
    private RSAPublicKey rsaPublicKey;
    @Value("${jwt.ttl}")
    private Duration ttl;

    @Bean
    public JwtEncoder jwtEncoder() {
        final var jwk = new RSAKey.Builder(rsaPublicKey)
                .privateKey(rsaPrivateKey).build();

        return new NimbusJwtEncoder(
                new ImmutableJWKSet<>(new JWKSet((JWK) jwk)));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

    @Bean
    public JwtService jwtService(@Value("${spring.application.name}") final String appName,
                                 final JwtEncoder jwtEncoder) {
        return new JwtService(appName, ttl, jwtEncoder);
    }


    /*
    Lombok's @Value -> is a class-level annotation that makes a class immutable
     and generates boilerplate code like getters, a constructor, and toString().

    Spring's @Value -> used for injecting values from the application's properties file
    or environment variables into a field, method parameter, or constructor parameter.
     */
}
