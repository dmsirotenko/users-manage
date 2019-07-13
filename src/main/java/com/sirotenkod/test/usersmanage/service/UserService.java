package com.sirotenkod.test.usersmanage.service;

import com.sirotenkod.test.usersmanage.dao.UserDAO;
import com.sirotenkod.test.usersmanage.dto.UserDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDAO createUser(UserDTO userDTO);
    List<UserDAO> createUsers(List<UserDTO> userDTOList);

    List<UserDAO> getUsers();
    List<UserDAO> getUsers(@Nullable Sort sort);

    List<UserDAO> searchUsers(Specification<UserDAO> spec);

    Optional<UserDAO> getUserById(Long id);

    UserDAO updateUser(UserDAO userDAO, UserDTO userDTO);

    void deleteUser(Long id);
}
