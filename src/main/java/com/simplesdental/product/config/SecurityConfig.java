package com.simplesdental.product.config;

import com.simplesdental.product.enums.UserRole;
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
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthenticationManager authenticationManager;

    public static final String[] OPEN_ROUTES = {
            "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**", "/api/*/auth/login", "/api/*/auth/register"
    };

    public static final String[] OPEN_ROUTES_REGEX = {
            "/swagger-ui.html", "/v3/api-docs/.*", "/swagger-ui/.*", "/api/v\\d+/auth/login", "/api/v\\d+/auth/register"
    };

    public static final RequestMatcher PRODUCTS_ROUTE_GET =
            new RegexRequestMatcher("^/api(/v\\d+)?/products.*", "GET");

    public static final RequestMatcher CATEGORIES_ROUTE_GET =
            new RegexRequestMatcher("^/api(/v\\d+)?/categories.*", "GET");

    public static final RequestMatcher AUTH_ROUTE_GET =
            new RegexRequestMatcher("^/api(/v\\d+)?/auth.*", "GET");

    public static final RequestMatcher USER_PASSWORD_ROUTE =
            new RegexRequestMatcher("^/api(/v\\d+)?/users/password", "PUT");

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserAuthenticationFilter userAuthenticationFilter) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a
                        .requestMatchers(OPEN_ROUTES).permitAll()
                        .requestMatchers(PRODUCTS_ROUTE_GET, CATEGORIES_ROUTE_GET, AUTH_ROUTE_GET, USER_PASSWORD_ROUTE)
                        .hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                        .anyRequest().hasAnyAuthority(UserRole.ADMIN.name()))
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }
}