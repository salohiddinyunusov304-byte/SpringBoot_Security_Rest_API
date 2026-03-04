package uz.pdp.springboot_security_rest_api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRoleAndAuthPermissionCheckController {

    @GetMapping("/has_admin_role")
    @PreAuthorize("hasRole('ADMIN')")
    public String has_admin_role() {
        return "has_admin_role";
    }

    @GetMapping("/has_user_role")
    @PreAuthorize("hasRole('USER')")
    public String has_user_role() {
        return "has_user_role";
    }

    @GetMapping("/has_admin_user_role")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String has_admin_user_role() {
        return "has_admin_user_role";
    }

    @GetMapping("/has_delete_book_permission")
    @PreAuthorize("hasAuthority('DELETE_BOOK')")
    public String has_delete_book_permission() {
        return "has_delete_book_permission";
    }

    @GetMapping("/has_view_book_permission")
    @PreAuthorize("hasAuthority('VIEW_BOOK')")
    public String has_view_book_permission() {
        return "has_view_book_permission";
    }
}
