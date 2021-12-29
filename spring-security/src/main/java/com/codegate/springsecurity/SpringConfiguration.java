package com.codegate.springsecurity;

import com.codegate.springsecurity.repositories.UserRepository;
import com.codegate.springsecurity.services.SSUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringConfiguration extends WebSecurityConfigurerAdapter {

  @Bean
  public static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  private SSUserDetailsService userDetailsService;

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return new SSUserDetailsService(userRepository);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/h2-console/**").permitAll()
        .antMatchers("/admin").access("hasAuthority('ADMIN')")
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login").permitAll()
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login").permitAll()
        .and()
        .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // auth.inMemoryAuthentication()
    // .withUser("otavio").password(passwordEncoder().encode("otavio")).authorities("ADMIN")
    // .and()
    // .withUser("user").password(passwordEncoder().encode("user")).authorities("USER");
    auth.userDetailsService(userDetailsServiceBean())
        .passwordEncoder(passwordEncoder());
  }
}
