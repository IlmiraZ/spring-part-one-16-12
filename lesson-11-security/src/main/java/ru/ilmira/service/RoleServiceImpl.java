package ru.ilmira.service;

import org.springframework.stereotype.Service;
import ru.ilmira.persist.Category;
import ru.ilmira.persist.Role;
import ru.ilmira.persist.RoleRepository;
import ru.ilmira.service.dto.CategoryDto;
import ru.ilmira.service.dto.RoleDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements  RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        return roleRepository.findById(id)
                .map(RoleServiceImpl::convertToDto);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = new Role(
                roleDto.getId(),
                roleDto.getName());
        Role saved = roleRepository.save(role);
        return convertToDto(saved);
    }

    private static RoleDto convertToDto(Role role) {
        return new RoleDto(role.getId(),role.getName());
    }
}
