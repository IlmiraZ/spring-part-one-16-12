package ru.ilmira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ilmira.persist.*;
import ru.ilmira.service.dto.UserDto;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public Page<UserDto> findAll(Optional<String> loginFilter,
                                 Integer page,
                                 Integer size,
                                 Sort sort
                                 ) {
        Specification<User> spec = Specification.where(null);
        if (loginFilter.isPresent() && !loginFilter.get().isBlank()) {
            spec = spec.and(UserSpecification.nameLike(loginFilter.get()));
        }
        return userRepository.findAll(spec, PageRequest.of(page, size,sort))
                .map(UserServiceImpl::convertToDto);
    }

    @Override
    public Optional<UserDto> findById(long id) {
        return userRepository.findById(id)
                .map(UserServiceImpl::convertToDto);
    }

    @Override
    public Optional<UserDto> findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(UserServiceImpl::convertToDto);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getLogin(),
                userDto.getPassword());
        User saved = userRepository.save(user);
        return convertToDto(saved);
    }

    private static UserDto convertToDto(User user) {
        return new UserDto(user.getId(),user.getLogin(), user.getPassword());
    }
}
