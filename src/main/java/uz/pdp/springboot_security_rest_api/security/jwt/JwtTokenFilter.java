package uz.pdp.springboot_security_rest_api.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
//OncePerRequestFilter - har bir request uchun faqat bir martta ishlaydi shuning uhcun session kerak emas
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9... - request keladi
        String authorization = request.getHeader("Authorization"); // Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9... oladi
        if (authorization == null || authorization.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7); // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9... - Token qism
        if (!jwtTokenUtil.isValidToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
        // mobil, chrome larni uchun ishlatib beradi buni
        WebAuthenticationDetails details = new WebAuthenticationDetails(request);
        authentication.setDetails(details);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
