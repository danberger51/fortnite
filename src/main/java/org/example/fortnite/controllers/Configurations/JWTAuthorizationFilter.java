package org.example.fortnite.controllers.Configurations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.example.fortnite.controllers.Configurations.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        // Überprüfe, ob der Request einen gültigen JWT-Token-Header enthält
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            // Wenn nicht, lasse den Request passieren
            chain.doFilter(req, res);
            return;
        }

        // Wenn ein gültiger Token-Header vorhanden ist, versuche, die Authentifizierung durchzuführen
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        // Setze die Authentifizierungsinformationen im SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Lasse den Request weiter durch die Filterkette gehen
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // Extrahiere den Token aus dem Header
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // Überprüfe und parse den Token
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                // Wenn der Benutzer aus dem Token extrahiert wurde, erstelle eine Authentifizierung
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }

            // Wenn der Benutzer nicht extrahiert werden konnte, gibt null zurück
            return null;
        }

        // Wenn kein Token vorhanden ist, gibt null zurück
        return null;
    }
}