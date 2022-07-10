package ru.abdullaeva.javacoursework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.abdullaeva.javacoursework.security.JwtUserDetailsService;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class JwtUserDetailsServiceTest {

    private final JwtUserDetailsService service;

    @Autowired
    JwtUserDetailsServiceTest(JwtUserDetailsService service) {
        this.service = service;
    }

    @Test
    void loadUserByUsername_ReturnFoundJwtUser() {
        String login = "user";
        Assertions.assertNotNull(service.loadUserByUsername(login));
    }
    @Test
    void loadUserByUsername_throwUsernameNotFoundException() {
        String login = "no exist";
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(login);
        });
    }
}