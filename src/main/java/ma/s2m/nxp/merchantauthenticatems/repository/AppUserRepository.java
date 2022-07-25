package ma.s2m.nxp.merchantauthenticatems.repository;

import ma.s2m.nxp.merchantauthenticatems.dto.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByusername(String username);
}
