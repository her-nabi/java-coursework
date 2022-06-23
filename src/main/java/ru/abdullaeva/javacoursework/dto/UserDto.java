package ru.abdullaeva.javacoursework.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.abdullaeva.javacoursework.model.auth.Users;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto {
    private UUID id;
    private String login;

    public Users toUser() {
        Users user = new Users();
        user.setId(id);
        user.setLogin(login);
        return user;
    }

    public static UserDto fromUser(Users user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        return userDto;
    }


}
