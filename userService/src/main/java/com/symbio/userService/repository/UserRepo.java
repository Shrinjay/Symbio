package com.symbio.userService.repository;

import com.symbio.userService.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Spring Data repository to access and modify items in the sponsors table
@Repository
public interface UserRepo extends JpaRepository<User, Long>{
    User findByUsername(String username);
}