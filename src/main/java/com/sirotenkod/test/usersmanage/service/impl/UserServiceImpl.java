package com.sirotenkod.test.usersmanage.service.impl;

import com.sirotenkod.test.usersmanage.dao.UserDAO;
import com.sirotenkod.test.usersmanage.dto.UserDTO;
import com.sirotenkod.test.usersmanage.repository.UserRepository;
import com.sirotenkod.test.usersmanage.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDAO createUser(UserDTO userDTO) {
        UserDAO userDAO = new UserDAO();

        return persistOrMerge(userDAO, userDTO);
    }

    @Override
    public List<UserDAO> getUsers() {
        List<UserDAO> users = new ArrayList<>();

        userRepository.findAll().iterator()
                .forEachRemaining(users::add);

        return users;
    }

    @Override
    public Optional<UserDAO> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDAO updateUser(UserDAO userDAO, UserDTO userDTO) {
        return persistOrMerge(userDAO, userDTO);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDAO persistOrMerge(UserDAO userDAO, UserDTO userDTO) {
        BeanUtils.copyProperties(userDTO, userDAO, "id");

        return userRepository.save(userDAO);
    }
}
