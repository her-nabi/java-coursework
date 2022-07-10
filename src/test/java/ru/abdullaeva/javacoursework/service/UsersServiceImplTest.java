package ru.abdullaeva.javacoursework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.abdullaeva.javacoursework.model.auth.Users;
import ru.abdullaeva.javacoursework.service.interf.UsersService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UsersServiceImplTest {

    private final UsersService usersService;

    @Autowired
    UsersServiceImplTest(UsersService usersService) {
        this.usersService = usersService;
    }

    @Test
    void getAll_ReturnUserList() {
        List<Users> usersList = usersService.getAll();
        Assertions.assertNotNull(usersList);
        Assertions.assertEquals(2,usersList.size());
    }

    @Test
    void findByLogin_ReturnFoundUser() {
        String login = "admin";
        Users user = usersService.findByLogin(login);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("admin", user.getLogin());

    }
    @Test
    void findByLogin_ThrowUsernameNotFoundException() {
        String login = "no exist";
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            usersService.findByLogin(login);
        });
    }

    @Test
    void findById_ReturnFoundUser() {
        String id = "a6ad6141-4bc7-46c0-ab5c-66489b4ca334";
        Users user = usersService.findById(UUID.fromString(id));
        Assertions.assertNotNull(user);
        Assertions.assertEquals("a6ad6141-4bc7-46c0-ab5c-66489b4ca334", user.getId().toString());
    }

    @Test
    void findById_ThrowNoSuchElementException() {
        String id = "00000000-4bc7-46c0-ab5c-66489b4ca334";
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            usersService.findById(UUID.fromString(id));
        });
    }
    }
