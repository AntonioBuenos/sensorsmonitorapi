package by.smirnov.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static by.smirnov.security.SecurityConstants.AUTH_HEADER_NAME;
import static by.smirnov.security.SecurityConstants.AUTH_HEADER_STARTS;
import static by.smirnov.security.SecurityConstants.INVALID_HEADER_TOKEN_MESSAGE;
import static by.smirnov.security.SecurityConstants.INVALID_TOKEN_MESSAGE;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsSecurityService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(AUTH_HEADER_NAME);

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith(AUTH_HEADER_STARTS)) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                response.sendError(SC_BAD_REQUEST, INVALID_HEADER_TOKEN_MESSAGE);
            } else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    UserDetails details = service.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            details,
                            details.getPassword(),
                            details.getAuthorities()
                    );

                    if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                } catch (JWTVerificationException e) {
                    response.sendError(SC_BAD_REQUEST, INVALID_TOKEN_MESSAGE);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
