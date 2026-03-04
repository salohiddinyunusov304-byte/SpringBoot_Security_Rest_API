package uz.pdp.springboot_security_rest_api.payload.auth;

public record TokenRequest(
        String username,
        String password
) {
}
