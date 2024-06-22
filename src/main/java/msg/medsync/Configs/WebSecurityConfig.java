package msg.medsync.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    public WebSecurityConfig() {
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //.requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name())
        );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //.requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .headers(headers -> headers.contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'none'; " +
                        "connect-src 'self' https://*.google-analytics.com https://*.analytics.google.com https://*.googletagmanager.com; " +
                        "style-src 'self' 'nonce-pdf-iframe'; " +
                        "script-src 'self' 'nonce-ga-tag' 'nonce-ga-script' https://*.googletagmanager.com https://www.google.com/recaptcha/ https://www.gstatic.com/recaptcha/; " +
                        "img-src 'self' blob: data: https://*.google-analytics.com https://*.googletagmanager.com https://www.google.com/recaptcha/ https://recaptcha.google.com/recaptcha/; " +
                        "frame-src 'self' blob: data: https://www.google.com/recaptcha/ https://recaptcha.google.com/recaptcha/; " +
                        "font-src 'self'")))
                .headers(headers -> headers.addHeaderWriter((request, response) -> {
                    // TODO: This is an unsafe CSP header. This should be changed in the future in order to completely ban inline css and js.
                    String uri = request.getRequestURI();
                    if (uri.equalsIgnoreCase("/download") || uri.equalsIgnoreCase("/profil") || uri.equalsIgnoreCase("/generieren/lebenslauf")) {
                        response.setHeader("Content-Security-Policy", "default-src 'none'; " +
                                "connect-src 'self' https://*.google-analytics.com https://*.analytics.google.com https://*.googletagmanager.com; " +
                                "style-src 'self' 'unsafe-inline'; " +
                                "script-src 'self' 'unsafe-inline' https://*.googletagmanager.com https://www.google.com/recaptcha/ https://www.gstatic.com/recaptcha/; " +
                                "img-src 'self' blob: data: https://*.google-analytics.com https://*.googletagmanager.com https://www.google.com/recaptcha/ https://recaptcha.google.com/recaptcha/; " +
                                "frame-src 'self' blob: data: https://www.google.com/recaptcha/ https://recaptcha.google.com/recaptcha/; " +
                                "font-src 'self'");
                    }
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/csrf/token").permitAll()
                        .requestMatchers("/api/user/register").permitAll()
                        .requestMatchers("/api/user/register/validate/*").permitAll()
                        .requestMatchers("/api/user/reset/password").permitAll()
                        .requestMatchers("/api/user/reset/password/validate/*").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/assets/**", "/robots.txt" , "/sitemap.txt").permitAll()
                        .requestMatchers("/", "/error", "/logout", "/home", "/registrieren", "/impressum", "/datenschutz", "/agb", "/passwort/vergessen", "/passwort/zurücksetzen/*", "/email").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/anmelden")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/anmelden?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                //.userDetailsService(jpaUserDetailsService)
                .authenticationProvider(authenticationProvider())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfFilter(CookieCsrfTokenRepository.withHttpOnlyFalse()), BasicAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8080")
        );
        configuration.setAllowedHeaders(List.of(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                "X-XSRF-TOKEN",
                "RECAPTCHA")
        );
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.OPTIONS.name())
        );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jpaUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }*/
}

