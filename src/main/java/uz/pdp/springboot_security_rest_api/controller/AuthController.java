package uz.pdp.springboot_security_rest_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.springboot_security_rest_api.entity.AuthUser;
import uz.pdp.springboot_security_rest_api.payload.auth.RegisterRequest;
import uz.pdp.springboot_security_rest_api.payload.auth.TokenRequest;
import uz.pdp.springboot_security_rest_api.repository.AuthRoleRepository;
import uz.pdp.springboot_security_rest_api.repository.AuthUserRepository;
import uz.pdp.springboot_security_rest_api.security.jwt.JwtTokenUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;

    @PostMapping("/token")
    public String token(@RequestBody TokenRequest tokenRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tokenRequest.username(), tokenRequest.password());
        authenticationManager.authenticate(authenticationToken); // shunday username bormi deb tekshiradi
        return jwtTokenUtil.generateToken(tokenRequest.username()) ;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        AuthUser authUser = AuthUser.builder()
                .fullName(registerRequest.fullName())
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .roles(authRoleRepository.findAllById(registerRequest.rolesIds()))
                .build();

        authUserRepository.save(authUser);
        return "Register Page";
    }
}
