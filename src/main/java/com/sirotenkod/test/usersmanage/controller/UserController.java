package com.sirotenkod.test.usersmanage.controller;

import com.sirotenkod.test.usersmanage.component.importer.exception.FailedImportException;
import com.sirotenkod.test.usersmanage.dao.UserDAO;
import com.sirotenkod.test.usersmanage.dto.UserDTO;
import com.sirotenkod.test.usersmanage.exception.BadRequestException;
import com.sirotenkod.test.usersmanage.exception.NotFoundException;
import com.sirotenkod.test.usersmanage.component.importer.UserImporter;
import com.sirotenkod.test.usersmanage.repository.specification.UserDAOSpecification;
import com.sirotenkod.test.usersmanage.service.UserService;
import com.sirotenkod.test.usersmanage.utils.SortUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final UserImporter userImporter;

    @Autowired
    public UserController(UserService userService, UserImporter userImporter) {
        this.userService = userService;
        this.userImporter = userImporter;
    }

    @GetMapping
    public @ResponseBody List<UserDTO> getUsers(@RequestParam(name = "sort", required = false) String[] sortParams) {
        Sort sort = null;

        if (!Objects.isNull(sortParams)) {
            sort = SortUtils.sortParamsToSort(sortParams);
        }

        return userService.getUsers(sort).stream()
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
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/import")
    public @ResponseBody List<UserDTO> importUsers(@RequestParam(name = "file") MultipartFile file) {
        try {
            return userImporter.importFromSheet(file.getInputStream()).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (FailedImportException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (IOException ex) {
            throw new BadRequestException();
        }
    }

    @GetMapping(value = "/search")
    public @ResponseBody List<UserDTO> searchUsers(@ModelAttribute UserDTO filter) {
        Specification<UserDAO> specification = new UserDAOSpecification(filter);

        return userService.searchUsers(specification).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDto(UserDAO userDAO) {
        UserDTO userDTO = new UserDTO();

        BeanUtils.copyProperties(userDAO, userDTO);

        return userDTO;
    }
}
