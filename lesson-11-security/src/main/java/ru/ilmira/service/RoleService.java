package ru.ilmira.service;

import ru.ilmira.service.dto.RoleDto;

import java.util.Optional;

public interface RoleService {

    Optional<RoleDto> findById(Long id);

    void deleteById(Long id);

    RoleDto save(RoleDto roleDto);


}
