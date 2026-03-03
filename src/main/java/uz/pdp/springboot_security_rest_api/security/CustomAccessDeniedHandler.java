package uz.pdp.springboot_security_rest_api.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import uz.pdp.springboot_security_rest_api.payload.ErrorDto;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            accessDeniedException.printStackTrace();
            String errorPath = request.getRequestURI();
            String errorMessage = "Role mavjud emas !!!"; // authException.getMessage();
            Integer errorCode = 403;
            ErrorDto errorDto = new ErrorDto(errorMessage, errorCode, errorPath);
            response.setStatus(errorCode);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, errorDto);
    }
}
