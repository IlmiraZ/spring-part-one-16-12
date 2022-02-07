package ru.ilmira.service.dto;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UserDto {
    private Long id;

    @NotBlank
    private String login;
    @NotBlank
    private String password;

    private Set<RoleDto> roles;

    public UserDto(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
