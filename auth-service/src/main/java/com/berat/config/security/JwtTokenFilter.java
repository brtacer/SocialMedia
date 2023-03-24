package com.berat.config.security;

import com.berat.exception.AuthManagerException;
import com.berat.exception.EErrorType;
import com.berat.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private JwtUserDetails jwtUserDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

        if (header!=null && header.startsWith("Bearer ") &&
                SecurityContextHolder.getContext().getAuthentication()==null){
            String token = header.substring(7);
            Optional<Long> id = jwtTokenManager.getIdFromToken(token);
            if (id.isPresent()){
                UserDetails userDetails = jwtUserDetails.loadUserById(id.get());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else {
                throw new AuthManagerException(EErrorType.INVALID_TOKEN);
            }
        }
        filterChain.doFilter(request,response);

    }
}
