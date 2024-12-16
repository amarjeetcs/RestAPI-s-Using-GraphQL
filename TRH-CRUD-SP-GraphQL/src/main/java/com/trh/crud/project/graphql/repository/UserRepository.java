package com.trh.crud.project.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trh.crud.project.graphql.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}