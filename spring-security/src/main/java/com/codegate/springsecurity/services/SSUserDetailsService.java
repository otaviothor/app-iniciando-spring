package com.codegate.springsecurity.services;

import java.util.Set;
import java.util.HashSet;
import com.codegate.springsecurity.models.Role;
import com.codegate.springsecurity.models.User;
import com.codegate.springsecurity.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SSUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public SSUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User user = userRepository.findByUsername(username);
      if (user == null) {
        return null;
      }
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
          getAuthories(user));
    }

    catch (Exception e) {
      throw new UsernameNotFoundException("User not found!");
    }
  }

  private Set<GrantedAuthority> getAuthories(User user) {

    Set<GrantedAuthority> authorities = new HashSet<>();
    for (Role role : user.getRoles()) {
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
      authorities.add(grantedAuthority);
    }
    return authorities;
  }
}
