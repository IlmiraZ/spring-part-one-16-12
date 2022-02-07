package ru.ilmira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ilmira.persist.Role;
import ru.ilmira.service.dto.RoleDto;
import ru.ilmira.service.dto.UserDto;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Override
    public Page<UserDto> findAll(Optional<String> loginFilter,
                                 Integer page,
                                 Integer size,
                                 String sortField,
                                 Sort.Direction direction) {
        return null;
    }

    @Override
    public Optional<UserDto> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public UserDto save(UserDto user) {
        return null;
    }


    private static RoleDto convertToDto(Role role) {
        return new RoleDto(role.getId(),role.getName());
    }
}
