package com.epam.lab.service.impl;

import com.epam.lab.dto.UserDetailsImpl;
import com.epam.lab.dto.UserDto;
import com.epam.lab.model.Role;
import com.epam.lab.model.User;
import com.epam.lab.repository.UserRepository;
import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);
        if (user != null) {
            UserDetailsImpl userDetails = new UserDetailsImpl();
            userDetails.setId(user.getId());
            userDetails.setLogin(user.getLogin());
            userDetails.setName(user.getName());
            userDetails.setPassword(user.getPassword());
            userDetails.setSurname(user.getSurname());
            userDetails.setAuthorities(user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toList()));
            return userDetails;
        } else {
            throw new UsernameNotFoundException("User with login = " + name + " does not exist");
        }
    }

    @Override
    @Transactional
    public void register(UserDto userDto) {
        if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
            User user = new User();
            user.setLogin(userDto.getLogin());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.getRoles().add(Role.ROLE_USER);
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            userRepository.register(user);
        }
    }
}
