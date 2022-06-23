package ru.abdullaeva.javacoursework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.abdullaeva.javacoursework.model.auth.Role;
import ru.abdullaeva.javacoursework.model.auth.Users;
import ru.abdullaeva.javacoursework.repository.RoleRepository;
import ru.abdullaeva.javacoursework.repository.UsersRepository;
import ru.abdullaeva.javacoursework.service.interf.UsersService;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, @Lazy BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Users> getAll() {
        List<Users> usersList = usersRepository.findAll();
        log.info("IN getAll - {} users found", usersList.size());
        return usersList;
    }

    @Override
    public Users findByLogin(String login) {
        Users result = usersRepository.findByLogin(login);
        log.info("IN findByLogin - user: {} found by login: {}", result, login);
        return result;
    }

    @Override
    public Users findById(UUID id) {
        Users result = usersRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }
        log.info("IN findById - user found by id: {}", result);
        return result;

    }


}
