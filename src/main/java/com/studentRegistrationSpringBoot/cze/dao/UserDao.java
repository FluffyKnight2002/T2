package com.studentRegistrationSpringBoot.cze.dao;


import com.studentRegistrationSpringBoot.cze.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
