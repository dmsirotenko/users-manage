package com.sirotenkod.test.usersmanage.service.impl;

import com.sirotenkod.test.usersmanage.dao.UserDAO;
import com.sirotenkod.test.usersmanage.dto.UserDTO;
import com.sirotenkod.test.usersmanage.repository.UserRepository;
import com.sirotenkod.test.usersmanage.service.UserService;
import org.hibernate.bytecode.enhance.internal.tracker.SortedFieldTracker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<UserDAO> createUsers(List<UserDTO> userDTOList) {
        return userDTOList.stream()
                .map(this::createUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDAO> getUsers() {
        return userRepository.findAll(getDefaultSort());
    }

    @Override
    public List<UserDAO> getUsers(@Nullable Sort sort) {
        if (Objects.isNull(sort)) {
            sort = getDefaultSort();
        }

        return userRepository.findAll(sort);
    }

    @Override
    public List<UserDAO> searchUsers(Specification<UserDAO> spec) {
        return userRepository.findAll(spec, getDefaultSort());
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

    private Sort getDefaultSort() {
        return Sort.by(Sort.Direction.DESC, "id");
    }
}
