package io.swagger.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider provider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = provider.resolveToken(httpServletRequest); // Retrieve the token from the request header

        try {
            if(token != null && provider.validateToken(token)){ // Validate token
                Authentication auth = provider.getAuthentication(token); // Retrieve the User
                SecurityContextHolder.getContext().setAuthentication(auth); // Set authentication for the retrieved User
            }
        }
        catch (ResponseStatusException ex){
            SecurityContextHolder.clearContext(); // Logout any User that's logged in
            httpServletResponse.sendError(ex.getStatus().value(), ex.getMessage()); // Send an error to the browser
            return; // Stop handling the request
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
