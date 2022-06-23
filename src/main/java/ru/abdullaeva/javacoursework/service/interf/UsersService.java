package ru.abdullaeva.javacoursework.service.interf;

import ru.abdullaeva.javacoursework.model.auth.Users;

import java.util.List;
import java.util.UUID;

public interface UsersService {

    List<Users> getAll();

    Users findByLogin(String login);

    Users findById(UUID id);

}
