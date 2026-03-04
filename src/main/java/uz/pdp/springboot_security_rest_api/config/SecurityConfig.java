package uz.pdp.springboot_security_rest_api.config;

import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tools.jackson.databind.ObjectMapper;
import uz.pdp.springboot_security_rest_api.payload.ErrorDto;
import uz.pdp.springboot_security_rest_api.security.CustomAccessDeniedHandler;
import uz.pdp.springboot_security_rest_api.security.CustomAuthenticationEntryPoint;

import java.util.List;

@Configuration
@EnableWebSecurity // SecurityConfig fayl security ni nazorat qiladi
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper; // Jaba ob -> JSON
    /// way - 1 (401)
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    /// way - 1 (403)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));
//                    "http://1.1.1.12:5001", "http://localhost:3000"));
                    configuration.setAllowedMethods(List.of("GET"));
                    configuration.setAllowedHeaders(List.of("*"));
//                    "X-G58-Header", "Content-Type"));

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    return source.getCorsConfiguration(request);
                }))
                .csrf(AbstractHttpConfigurer::disable) // csrf ni disable qiladi
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest()
                        .fullyAuthenticated() // remember me degan narsa bolmaydi doim har sfar login qiladi
                ) // istalgan sorovga fullyAutheticated ni taminlaydi
                .httpBasic((httpBasicConfigurer -> httpBasicConfigurer.realmName("G58")
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                ))
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                        // .authenticationEntryPoint(customAuthenticationEntryPoint))
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        return request -> {
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            var corsConfiguration = new CorsConfiguration();
//            corsConfiguration.setAllowedOrigins(List.of("*"));
////                    "http://1.1.1.12:5001", "http://localhost:3000"));
//            corsConfiguration.setAllowedMethods(List.of("GET"));
//            corsConfiguration.setAllowedHeaders(List.of("*"));
////                    "X-G58-Header", "Content-Type"));
//            source.registerCorsConfiguration("/**", corsConfiguration);
//            return source.getCorsConfiguration(request);
//        };
//    }


        /// way - 2 (401)
//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        return (request, response, authException) -> {
//            authException.printStackTrace();
//            String errorPath = request.getRequestURI();
//            String errorMessage = "Avvalo login boling !!!"; // authException.getMessage();
//            Integer errorCode = 401;
//            ErrorDto errorDto = new ErrorDto(errorMessage, errorCode, errorPath);
//            response.setStatus(errorCode);
//            ServletOutputStream outputStream = response.getOutputStream();
//            objectMapper.writeValue(outputStream, errorDto);
//        };
//    }

    /// way - 2 (403)
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return (request, response, accessDeniedException) -> {
//            accessDeniedException.printStackTrace();
//            String errorPath = request.getRequestURI();
//            String errorMessage = "Role mavjud emas !!!"; // authException.getMessage();
//            Integer errorCode = 403;
//            ErrorDto errorDto = new ErrorDto(errorMessage, errorCode, errorPath);
//            response.setStatus(errorCode);
//            ServletOutputStream outputStream = response.getOutputStream();
//            objectMapper.writeValue(outputStream, errorDto);
//        };
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("123")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("123")
                .roles("USER", "ADMIN")
                .build();

        UserDetails manager = User.builder()
                .username("manager")
                .password("123")
                .roles("MANAGER")
                .build();

        return new InMemoryUserDetailsManager(user, admin, manager);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // faqat test uchun
    }
}
