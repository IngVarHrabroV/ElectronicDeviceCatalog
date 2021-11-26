package com.hrabrov.electronic_device_catalog.controller;

import com.hrabrov.electronic_device_catalog.domain.Role;
import com.hrabrov.electronic_device_catalog.domain.User;
import com.hrabrov.electronic_device_catalog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          Map<String, Object> model) {
        if ((username == null || username.isEmpty())
                || (password == null || password.isEmpty())
        ) {
            model.put("message", "Не был введен логин или пароль");
            return "registration";
        }

        if (isUserExistInDb(username)) {
            model.put("message", "Пользователь с таким именем уже существует");
            return "registration";
        }

        User newUser = new User(username, password, true, Collections.singleton(Role.USER));

        userRepository.save(newUser);

        return "redirect:/login";
    }

    private boolean isUserExistInDb(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @GetMapping("/login")
    public String authorizationError(@RequestParam(required = false) String error,
                                     Map<String, Object> model) {
        if (error != null) {
            model.put("authorizationError", true);
        }

        return "/login";
    }
}
