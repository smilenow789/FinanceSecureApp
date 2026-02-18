package com.example.financesecureapp;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Behebt "Strict-Transport-Security Header Not Set"
        httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");

        // Behebt "X-Content-Type-Options Header Missing"
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");

        // Behebt "Cross-Origin-Resource-Policy Header Missing or Invalid"
        httpResponse.setHeader("Cross-Origin-Resource-Policy", "same-origin");

        // Behebt "Re-examine Cache-control Directives" & "Storable and Cacheable Content"
        httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        httpResponse.setHeader("Pragma", "no-cache");

        // Weitergabe an die eigentliche Anwendung
        chain.doFilter(request, response);
    }
}