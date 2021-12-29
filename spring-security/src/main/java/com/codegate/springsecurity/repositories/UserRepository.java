package com.codegate.springsecurity.repositories;

import com.codegate.springsecurity.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);
}
