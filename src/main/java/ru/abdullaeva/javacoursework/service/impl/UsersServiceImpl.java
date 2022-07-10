package ru.abdullaeva.javacoursework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.abdullaeva.javacoursework.model.auth.Users;
import ru.abdullaeva.javacoursework.repository.UsersRepository;
import ru.abdullaeva.javacoursework.service.interf.UsersService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<Users> getAll() {
        List<Users> usersList = usersRepository.findAll();
        log.info("В getAll найдено данное количество юзеров: {} ", usersList.size());
        return usersList;
    }

    @Override
    public Users findByLogin(String login) {
        Users result = usersRepository.findByLogin(login);
        log.info("В findByLogin был найден пользователь: {} с логином: {}", result, login);
        if (result != null) {
            return result;
        } else {
            throw new UsernameNotFoundException("Пользователя с данным логином не существует.");
        }
    }

    @Override
    public Users findById(UUID id) {
        Users result = usersRepository.findById(id).orElse(null);

        if (result == null) {
            throw new NoSuchElementException("Пользователя с таким id не существует.");
        }
        log.info("В findById был найлден пользователь с id: {}", result);
        return result;
    }
}
