package org.culpan.champ3.security;

import org.culpan.champ3.users.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaUserDetailsService implements UserDetailsService {
  private final UserRepository users;

  public JpaUserDetailsService(UserRepository users) {
    this.users = users;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var u = users.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException(email));
    var auths = u.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();

    return org.springframework.security.core.userdetails.User
        .withUsername(u.getEmail())
        .password(u.getPasswordHash()) // stored as {bcrypt}... (delegating encoder)
        .authorities(auths)
        .accountLocked(!u.isEnabled())
        .build();
  }
}