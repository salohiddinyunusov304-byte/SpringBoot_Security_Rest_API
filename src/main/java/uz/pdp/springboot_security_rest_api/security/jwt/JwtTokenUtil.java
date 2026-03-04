package uz.pdp.springboot_security_rest_api.security.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final String SECRET_KEY = "4M4iy2mPqkOBVNs3QOYpE9PrbjWkgXPzJN1fHFhUT4t";

    public String generateToken(@NonNull String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("http://g58.uz")
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60))
                .signWith(signKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key signKey() {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}
