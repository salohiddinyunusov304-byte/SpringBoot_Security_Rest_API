package uz.pdp.springboot_security_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.springboot_security_rest_api.entity.AuthPermission;

import java.util.List;

public interface AuthPermissionRepository extends JpaRepository<AuthPermission, Integer> {
    @Query(nativeQuery = true, value = "select ap.* from auth_permissions ap join auth_role_permissions arp on ap.id = arp.permission_id where arp.role_id = :roleId")
    List<AuthPermission> findAuthPermissionsByRoleId(@Param("roleId") Integer id);
}
