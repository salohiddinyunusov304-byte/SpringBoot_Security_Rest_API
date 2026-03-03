package uz.pdp.springboot_security_rest_api.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import uz.pdp.springboot_security_rest_api.payload.ErrorDto;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            authException.printStackTrace();
            String errorPath = request.getRequestURI();
            String errorMessage = "Avvalo login boling !!!"; // authException.getMessage();
            Integer errorCode = 401;
            ErrorDto errorDto = new ErrorDto(errorMessage, errorCode, errorPath);
            response.setStatus(errorCode);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, errorDto);
    }
}
