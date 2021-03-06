package ru.ilmira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.ilmira.service.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Page<UserDto> findAll(Optional<String> loginFilter,
                          Integer page,
                          Integer size,
                          Sort sort);

    Optional<UserDto> findById(long id);

    Optional<UserDto> findByLogin(String login);

    void deleteById(Long id);

    UserDto save(UserDto user);
}
