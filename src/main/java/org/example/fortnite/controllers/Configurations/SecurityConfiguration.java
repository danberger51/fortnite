package org.example.fortnite.controllers.Configurations;

import org.example.fortnite.controllers.Services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.example.fortnite.controllers.Configurations.SecurityConstants.SIGN_UP_URL;
import static org.example.fortnite.controllers.Configurations.SecurityConstants.API_DOCUMENTATION_URLS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    // UserDetailsServiceImpl ist eine Implementierung des UserDetailsService-Interfaces von Spring Security.
    // Es wird verwendet, um Benutzerinformationen zu laden.
    private final UserDetailsServiceImpl userDetailsService;

    // bCryptPasswordEncoder wird verwendet, um Passwörter zu hashen und zu überprüfen.
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // Konstruktor, der die erforderlichen Abhängigkeiten injiziert.
    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Konfiguration der HTTP-Sicherheitseinstellungen.
        http.cors().and().csrf().disable() // CORS und CSRF-Schutz deaktivieren.
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll() // Erlaube POST-Anfragen auf SIGN_UP_URL ohne Authentifizierung.
                .antMatchers(HttpMethod.GET, API_DOCUMENTATION_URLS).permitAll() // Erlaube GET-Anfragen auf API_DOCUMENTATION_URLS ohne Authentifizierung.
                .anyRequest().authenticated() // Alle anderen Anfragen erfordern Authentifizierung.
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager())) // Füge benutzerdefinierten Filter für die Authentifizierung hinzu.
                .addFilter(new JWTAuthorizationFilter(authenticationManager())) // Füge benutzerdefinierten Filter für die Autorisierung hinzu.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Deaktiviere Sitzungserstellung (RESTful-Anwendung).
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Konfiguration des AuthenticationManagerBuilders.
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // Bean für die Konfiguration von CORS (Cross-Origin Resource Sharing).
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Bean für den AuthenticationManager.
        return super.authenticationManagerBean();
    }
}
