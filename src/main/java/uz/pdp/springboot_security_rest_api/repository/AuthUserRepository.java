package uz.pdp.springboot_security_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springboot_security_rest_api.entity.AuthUser;
import java.util.Optional;

public interface AuthUserRepository  extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser > findByUsername(String username);
}
