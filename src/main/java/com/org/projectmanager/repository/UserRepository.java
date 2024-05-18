package com.org.projectmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.projectmanager.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);

}
