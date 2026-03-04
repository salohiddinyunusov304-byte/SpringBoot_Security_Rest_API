package uz.pdp.springboot_security_rest_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.springboot_security_rest_api.payload.TokenRequest;
import uz.pdp.springboot_security_rest_api.security.jwt.JwtTokenUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/token")
    public String token(@RequestBody TokenRequest tokenRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRequest.username());

        if (userDetails == null || !passwordEncoder.matches(tokenRequest.password(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return jwtTokenUtil.generateToken(tokenRequest.username()) ;
    }
}
