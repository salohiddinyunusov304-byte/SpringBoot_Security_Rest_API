package uz.pdp.springboot_security_rest_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.springboot_security_rest_api.entity.AuthRole;
import uz.pdp.springboot_security_rest_api.entity.AuthUser;
import uz.pdp.springboot_security_rest_api.repository.AuthPermissionRepository;
import uz.pdp.springboot_security_rest_api.repository.AuthRoleRepository;
import uz.pdp.springboot_security_rest_api.repository.AuthUserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;
    private final AuthPermissionRepository authPermissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));

        var roles = authRoleRepository.findAuthRolesByUserId(authUser.getId());
        for (AuthRole role : roles) {
            var permissions = authPermissionRepository.findAuthPermissionsByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        authUser.setRoles(roles);

        return new CustomUserDetails(authUser);
    }

}
