package ru.abdullaeva.javacoursework.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.abdullaeva.javacoursework.model.auth.Users;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
    private UUID id;
    private String login;

    public Users toUser() {
        Users user = new Users();
        user.setId(id);
        user.getLogin();
        return user;
    }

    public static AdminUserDto fromUser(Users user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setLogin(user.getLogin());
        return adminUserDto;
    }
}
