package com.javatechie.crud.example.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImpl detailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getToken(request);
            if (token != null && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);

                UserDetails userDetails = detailsServiceImpl.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer")) {
            return header.replace("Bearer", "");
        } else {
            return null;
        }
    }
}
