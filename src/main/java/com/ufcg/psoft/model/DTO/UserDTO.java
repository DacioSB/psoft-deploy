package com.ufcg.psoft.model.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.ufcg.psoft.model.User;

public class UserDTO {

    private String login;

    public UserDTO(User user) {
        this.login = user.getLogin();
    }

    public String getLogin() {
        return login;
    }

    public static List<UserDTO> convert(List<User> users) {
		return users.stream().map(UserDTO::new).collect(Collectors.toList());
	}
}
