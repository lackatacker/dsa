package ma.s2m.nxp.merchantauthenticatems.repository;

import ma.s2m.nxp.merchantauthenticatems.dto.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String roleName);
}