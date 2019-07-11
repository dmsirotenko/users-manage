package com.sirotenkod.test.usersmanage.repository;

import com.sirotenkod.test.usersmanage.dao.UserDAO;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDAO, Long> { }
