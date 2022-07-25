/*
 * Copyright S2M 2020-2021 the original author or authors.
 *
 * you may not use this file except in compliance with the S2M License.
 * You may obtain a copy of the License from S2M
 *
 *      https://www.s2mworldwide.com
 *
 * Auteur  : S2M
 * Contact : www.s2mworldwide.com
 *
 */

package ma.s2m.nxp.merchantauthenticatems;

import ma.s2m.nxp.merchantauthenticatems.dto.AppRole;
import ma.s2m.nxp.merchantauthenticatems.dto.AppUser;
import ma.s2m.nxp.merchantauthenticatems.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Main Class.
 *
 * @author S2M
 */

@SpringBootApplication
@ComponentScan("ma.s2m")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)

public class MainApplication {
    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(AccountService accountService) {
        return args -> {
            accountService.addNewRole(new AppRole(null, "USER"));
            accountService.addNewRole(new AppRole(null, "ADMIN"));

            accountService.addNewUser(new AppUser(null, "user", "password", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "admin", "password", new ArrayList<>()));

            accountService.addRoleToUser("MERCHANT", "user");
            accountService.addRoleToUser("ADMIN", "admin");

        };
    }
}