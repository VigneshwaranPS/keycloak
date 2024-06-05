package com.example.keycloak4;


import java.io.IOException;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.adapters.authorization.spi.ConfigurationResolver;
import org.keycloak.adapters.authorization.spi.HttpRequest;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.JsonSerialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("\n\n----------------Security Filter chain------------------\n\n");

        http.csrf(t->t.disable());

        http.addFilterAfter(createPloicyEnforcerFilter(), BearerTokenAuthenticationFilter.class);

        http.sessionManagement((
                t->t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ));

        return http.build();
    }

    private ServletPolicyEnforcerFilter createPloicyEnforcerFilter(){

        System.out.println("\n\n----------------Servlet Policy------------------\n\n");


        return new ServletPolicyEnforcerFilter(new ConfigurationResolver() {
            @Override
            public PolicyEnforcerConfig resolve(HttpRequest httpRequest) {
                try {
                    return JsonSerialization.
                            readValue(getClass().getResourceAsStream("/policy-enforcer.json"),
                                    PolicyEnforcerConfig.class);
                }catch (IOException e){
                    throw new
                            RuntimeException(e);
                }
            }
        });
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//
//        System.out.println("\n\n----------------Web Configerer------------------\n\n");
//
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                        .allowedHeaders("authorization", "content-type", "x-auth-token")
//                        .exposedHeaders("x-auth-token");
//            }
//        };
//    }
}
