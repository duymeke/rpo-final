package com.example.demo.services.impl;


import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.Permission;
import com.example.demo.models.User;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper mapper;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<UserDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public UserDto getById(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    @Override
    public UserDto create(UserCreateDto userCreateDto) {
        if (Objects.isNull(userCreateDto)) return null;
        User responseUser = new User();

        User check = repository.findByEmail(userCreateDto.getEmail());
        if (check == null){
            User newUser = new User();
            newUser.setUsername(userCreateDto.getUsername());
            newUser.setEmail(userCreateDto.getEmail());
            newUser.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
            List<Permission> permissions = List.of(permissionRepository.findByName("ROLE_USER"));

            newUser.setPermissions(permissions);
            responseUser = repository.save(newUser);
        }


        return mapper.toDto(responseUser);
    }

    @Override
    public UserDto update(Long id, UserCreateDto userCreateDto) {
        User old = repository.findById(id).orElse(null);

        if (Objects.isNull(userCreateDto) || Objects.isNull(old)) {
            return null;
        }

        old.setUsername(userCreateDto.getUsername());
        old.setEmail(userCreateDto.getUsername());

        if (userCreateDto.getPassword() != null && !userCreateDto.getPassword().isEmpty()) {
            old.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        }


        return mapper.toDto(repository.save(old));
    }

    @Override
    public boolean delete(Long id) {

        repository.deleteById(id);

        UserDto delete = getById(id);

        if (Objects.isNull(delete)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);

        if (Objects.nonNull(user)) {
            return user;
        }

        throw new UsernameNotFoundException("User not found");
    }
}
