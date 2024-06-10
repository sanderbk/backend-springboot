package io.swagger.service;

import io.swagger.model.entity.User;
import io.swagger.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User '" + username + "' not found in DB");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(user.getUserTypes()) //user.getUserTypes() //List.of(user.getUserType())
                .accountExpired(false)
                .accountLocked(false)
                .disabled(false)
                .credentialsExpired(false)
                .build();
    }
}
