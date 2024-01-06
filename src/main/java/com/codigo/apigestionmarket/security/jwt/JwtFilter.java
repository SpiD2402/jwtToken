package com.codigo.apigestionmarket.security.jwt;

import com.codigo.apigestionmarket.security.CustomerDetailService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private  JwtUtil jwtUtil;
    private String username = null;

    @Autowired
    private CustomerDetailService customerDetailService;
    Claims claims = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().matches("/usuarios/singup|/usuarios/login"))
        {
            filterChain.doFilter(request,response);
        }
        else {
                String authorizacionHeader = request.getHeader("Authorization");
                    String token= null;

                    if (authorizacionHeader!= null && authorizacionHeader.startsWith("Bearer "))
                    {
                        token = authorizacionHeader.substring(7);
                        username = jwtUtil.extractUserName(token);
                        claims = jwtUtil.extraAllClaims(token);

                    }
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
                    {
                        UserDetails userDetails =customerDetailService.loadUserByUsername(username);
                        if (jwtUtil.validateToken(token,userDetails))
                        {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                            new WebAuthenticationDetailsSource().buildDetails(request);
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


                        }
                    }
                    filterChain.doFilter(request,response);
        }
    }
}
