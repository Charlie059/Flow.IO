package org.flowio.authenticationservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.flowio.authenticationservice.service.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * This method is used to filter incoming requests and authenticate them.
     * It extracts the JWT token from the Authorization header and validates it.
     * If the token is valid, it sets the authentication in the security context.
     * If the token is invalid or not present, it forwards the request to the next filter in the chain.
     *
     * @param request the incoming HTTP request
     * @param response the outgoing HTTP response
     * @param filterChain the chain of filters that the request should go through
     * @throws ServletException if the request could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt, userEmail;

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader == null || ! authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header
        jwt = authHeader.substring(7); // after "Bearer "
        if (! "accessToken".equals(jwtService.extractTokenType(jwt))){
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the user email from the JWT token
        userEmail = jwtService.extractUserEmail(jwt);

        // If the user email is not null and there is no authentication in the security context,
        // load the user details and validate the token
        if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        // If the token is valid, set the authentication in the security context

        final String jwtUserEmail = jwtService.extractUserEmail(jwt);
        if (!Objects.equals(userDetails.getUsername(), jwtUserEmail)) {
            filterChain.doFilter(request, response);
            return;
        }


        if (jwtService.isTokenValid(jwt)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Forward the request to the next filter in the chain
        filterChain.doFilter(request, response);

    }
}
