package ru.abdullaeva.javacoursework.service.interf;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.abdullaeva.javacoursework.model.auth.Users;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface UsersService {

    /**
     * Метод возвращает список всех пользователей.
     * @return список пользователей.
     */
    List<Users> getAll();

    /**
     * Поиск пользователя по логину.
     * @param login логин пользователя.
     * @exception UsernameNotFoundException выбрасывается в том случае, если пользователь был не найден.
     * @return найденный пользователь.
     */
    Users findByLogin(String login);

    /**
     * Поиск пользователя по идентификатору.
     * @param id идеинтификатор пользователя.
     * @exception NoSuchElementException выбрасывается в том случае, если пользователь был не найден.
     * @return найденный пользователь.
     */
    Users findById(UUID id);

}
