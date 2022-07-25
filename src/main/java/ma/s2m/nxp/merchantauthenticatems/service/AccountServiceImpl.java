package ma.s2m.nxp.merchantauthenticatems.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.s2m.nxp.merchantauthenticatems.dto.AppRole;
import ma.s2m.nxp.merchantauthenticatems.dto.AppUser;
import ma.s2m.nxp.merchantauthenticatems.repository.AppRoleRepository;
import ma.s2m.nxp.merchantauthenticatems.repository.AppUserRepository;
import ma.s2m.nxp.merchantauthenticatems.utils.JWTutils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    final private AppRoleRepository appRoleRepository;
    final private AppUserRepository appUserRepository;
    final private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {
        String pw=appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void deleteUser(Long id) {
        if(!appUserRepository.existsById(id))
            throw new IllegalStateException("User doesn't exist!");
        appUserRepository.deleteById(id);
    }

    @Override
    public void addRoleToUser(String roleName, String username) {
        AppUser appUser = appUserRepository.findByusername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByusername(String username) {
        return appUserRepository.findByusername(username);
    }

    @Override
    public List<AppUser> listUsers() {

        return appUserRepository.findAll();
    }
    @Override
    public List<AppRole> listRoles() {

        return appRoleRepository.findAll();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String refreshToken = request.getHeader(JWTutils.AUTH_HEADER);
        if (refreshToken != null && refreshToken.startsWith(JWTutils.TOKEN_PREFIX)) {
            try {
                String jwt = refreshToken.substring(JWTutils.TOKEN_PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JWTutils.SECRET_KEY);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                AppUser appUser = loadUserByusername(username);
                String jwtAccessToken= JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+JWTutils.TIME_OUT_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",appUser.getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> dualToken = new HashMap<>();
                dualToken.put("access-token",jwtAccessToken);
                dualToken.put("refresh-token",jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),dualToken);
            } catch (Exception e) {
                response.setHeader("Error Message", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }}
        else{
            throw new RuntimeException("Refresh Token Required!");
        }
    }
}
