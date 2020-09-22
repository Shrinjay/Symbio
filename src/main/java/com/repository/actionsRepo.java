package com.repository;

import com.models.actions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface actionsRepo extends JpaRepository<actions, String>{
    
}