package org.example.fortnite.controllers.Configurations;

import org.example.fortnite.controllers.Services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, API_DOCUMENTATION_URLS).permitAll()
                .antMatchers(HttpMethod.GET, "/weapons/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/weapons").permitAll()
                .antMatchers(HttpMethod.POST, "/weapons/insert").permitAll()
                .antMatchers(HttpMethod.DELETE, "/weapons/").permitAll()
                .antMatchers(HttpMethod.GET, "/skins/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/skins").permitAll()
                .antMatchers(HttpMethod.POST, "/skins/insert").permitAll()
                .antMatchers(HttpMethod.DELETE, "/skins/").permitAll()
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/").permitAll()
                .antMatchers(HttpMethod.POST, "/users/sign-up").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
