package com.gaurav.blog.security;

import java.io.IOException;
import java.security.Security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuhtenticationFilter extends OncePerRequestFilter {
    // private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    

    //this method will be called for every request      
    //it will check if the token is valid or not
    // if valid then it will set the authentication in the security context
    // if not valid then it will not set the authentication in the security context 
    // and the request will be rejected by the security filter chain
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get Token
        String requestToken = request.getHeader("Authorization");

        // Bearer 234324.234234.234234
        System.out.println(requestToken);
        String username = null;
        String token = null;
        if(requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid Token, not start with Bearer string");
        }

        // validate token
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if(this.jwtTokenHelper.validateToken(token, userDetails)) {
                // set the authentication
                // authentication is the object which contains the user details and the authorities
                // and is used to check if the user is authenticated or not
                // and is used to check if the user has the required authorities to access a resource
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                // set the details of the authentication
                // details is the object which contains the details of the request
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Invalid JWT Token");
            }
        } else {
            System.out.println("Username is null or context is not null");
        }
      // pass the request to the next filter
      // if the token is valid then the request will be passed to the next filter
      // if the token is not valid then the request will be rejected by the security filter chain
        filterChain.doFilter(request, response);
    }

}
