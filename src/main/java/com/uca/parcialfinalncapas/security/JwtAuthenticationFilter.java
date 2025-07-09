package com.uca.parcialfinalncapas.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // 1) Si es login o registro, no toques nada, deja pasar:
        String path = request.getServletPath();
        if (path.startsWith("/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        // 2) Lectura del header Authorization
        String header = request.getHeader("Authorization");
        String token = null, username = null;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception ex) {
                // token inválido → corta y responde 401
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }
        }

        // 3) Si hay username y aún no hay autenticación en contexto
        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 4) Validar token
            if (jwtUtil.validateToken(token, userDetails)) {
                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirado o inválido");
                return;
            }
        }

        // 5) Continúa con la cadena
        chain.doFilter(request, response);
    }
}
