package com.psl.service;

import java.util.Collection;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IUserDAO;
import com.psl.entity.User;

import lombok.AllArgsConstructor;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserDAO userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByEmail(username);
        User user = userOptional.orElseThrow(() -> {
            log.error("No user " + "Found with username : " + username);
            return new UsernameNotFoundException("No user " +
                    "Found with username : " + username);
        });

        if (user.getRoleId().getRoleId() == 1) {
            return new org.springframework.security
                    .core.userdetails.User(user.getEmail(), user.getPassword(),
                    user.isEnabled(), true, true,
                    true, getAuthorities("ADMIN"));
        }
        if (user.getRoleId().getRoleId() == 2) {
            return new org.springframework.security
                    .core.userdetails.User(user.getEmail(), user.getPassword(),
                    user.isEnabled(), true, true,
                    true, getAuthorities("OWNER"));
        }
        return new org.springframework.security
                .core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }

}
