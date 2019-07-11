package com.sirotenkod.test.usersmanage.controller;

import com.sirotenkod.test.usersmanage.dao.UserDAO;
import com.sirotenkod.test.usersmanage.dto.UserDTO;
import com.sirotenkod.test.usersmanage.exception.NotFoundException;
import com.sirotenkod.test.usersmanage.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody List<UserDTO> getUsers() {
        return userService.getUsers().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Validated UserDTO userDTO) {
        UserDAO userDAO = userService.createUser(userDTO);

        return new ResponseEntity<>(convertToDto(userDAO), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(this::convertToDto)
                .orElseThrow(NotFoundException::new);
    }

    @PatchMapping(value = "/{id}")
    public @ResponseBody UserDTO updateUser(@PathVariable Long id, @RequestBody @Validated UserDTO userDTO) {
        UserDAO userDAO = userService.getUserById(id)
                .orElseThrow(NotFoundException::new);

        UserDAO updatedUser = userService.updateUser(userDAO, userDTO);

        return convertToDto(updatedUser);
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UserDTO convertToDto(UserDAO userDAO) {
        UserDTO userDTO = new UserDTO();

        BeanUtils.copyProperties(userDAO, userDTO);

        return userDTO;
    }
}
