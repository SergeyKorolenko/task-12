package com.epam.lab.controller;

import com.epam.lab.dto.AuthUserDto;
import com.epam.lab.dto.UserDetailsImpl;
import com.epam.lab.dto.UserDto;
import com.epam.lab.jwt.JwtTokenProvider;
import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping(value = "/users")
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signIn")
    public ResponseEntity<?> signIn(@RequestBody AuthUserDto authUserDto) throws Exception {
        try {
            String username = authUserDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authUserDto.getPassword()));
            UserDetails userDetails = userService.loadUserByUsername(username);
            String token = jwtTokenProvider.createToken(username, userDetails
                    .getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            UserDetailsImpl userInfo = (UserDetailsImpl) userDetails;
            Map<Object, Object> model = new HashMap<>();
            model.put("login", username);
            model.put("name", userInfo.getName());
            model.put("surname", userInfo.getSurname());
            model.put("roles", userInfo.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping(value = "/signUp")
    public void register(@RequestBody UserDto userDto) {
        userService.register(userDto);
    }

}
