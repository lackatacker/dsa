package ma.s2m.nxp.merchantauthenticatems.service;

import ma.s2m.nxp.merchantauthenticatems.dto.AppRole;
import ma.s2m.nxp.merchantauthenticatems.dto.AppUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void deleteUser(Long id);
    void addRoleToUser(String roleName, String username);
    AppUser loadUserByusername(String username);
    List<AppUser> listUsers();
    List<AppRole> listRoles();
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
