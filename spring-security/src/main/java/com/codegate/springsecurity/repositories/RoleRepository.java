package com.codegate.springsecurity.repositories;

import com.codegate.springsecurity.models.Role;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
  Role findByRole(String role);
}