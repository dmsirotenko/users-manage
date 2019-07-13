package com.sirotenkod.test.usersmanage.repository;

import com.sirotenkod.test.usersmanage.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserDAO, Long>, JpaSpecificationExecutor<UserDAO> { }
