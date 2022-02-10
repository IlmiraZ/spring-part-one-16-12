package ru.ilmira.service;

import ru.ilmira.service.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleDto> findAll();

    Optional<RoleDto> findById(Long id);

    void deleteById(Long id);

    RoleDto save(RoleDto roleDto);


}
