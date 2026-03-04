package uz.pdp.springboot_security_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.springboot_security_rest_api.entity.AuthRole;

import java.util.List;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Integer> {
    @Query(nativeQuery = true, value = "select ar.* from auth_roles ar join auth_user_roles aur on aur.role_id = ar.id where aur.user_id = :userId")
    List<AuthRole> findAuthRolesByUserId(@Param("userId") Long id);

}
