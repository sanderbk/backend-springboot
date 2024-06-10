package io.swagger.service;

import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.dto.TokenDTO;
import io.swagger.model.entity.User;
import io.swagger.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder encoder;

    public TokenDTO login(String username, String password) {

        TokenDTO tokenDto = new TokenDTO();
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)); // Check username and password via Spring Boot Security
            User user = this.findByUsername(username);
            tokenDto.setToken(provider.createToken(username, user.getUserTypes()));
            tokenDto.setUserName(user.getUsername());
            tokenDto.setUserrole(user.getUserTypes());
            tokenDto.setUserId(user.getId());
        } catch (AuthenticationException authEx) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username and/or password");
        }

        return tokenDto;
    }

    public User addUser(User user) {

        user.setPassword(encoder.encode(user.getPassword()));

        return Optional.of(userRepo.save(user)).orElseThrow(
                () ->  new NoSuchElementException("Something went wrong; the server couldn't respond with new User object"));
    }

    public User updateUser(User updatedUser) {

        // Check if User with given id exists before updating
        if(this.findById(updatedUser.getId()) == null){
            throw new IllegalArgumentException("The user with the requested ID" + " (" + updatedUser.getId() + ") " + "could not be updated; user does not exist");
        }

        updatedUser.setPassword(encoder.encode(updatedUser.getPassword()));

        return Optional.of(userRepo.save(updatedUser)).orElseThrow(
                () ->  new NoSuchElementException("Something went wrong; the server couldn't respond with new User object"));
    }

    public List<User> getAll(Integer skip, Integer limit) {

        if(skip == null){
            skip = 0;
        }

        if(limit == null){
            limit = Integer.MAX_VALUE;
        }

        Pageable pageable = PageRequest.of(skip, limit);
        return userRepo.findAll(pageable).getContent();
    }

    public List<User> getAllWithoutAccount() {
        return userRepo.findAllWithoutAccount();
    }

    // All findBy methods retrieve an Optional<User> from the repo

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    private User findByPhone(String phone) {
        return userRepo.findByPhone(phone).orElse(null);
    }

    public User findById(UUID id) {
        return userRepo.findById(id).orElse(null);
    }

    public void doesUserDataExist(User user) {

        // Check if a user exists, that is not the same as the given user, with the given user's username
        if (findByUsername(user.getUsername()) != null && !findByUsername(user.getUsername()).equals(user)) {
            throw new IllegalArgumentException("Username is already in use! Please try again");
        }

        // Check if a user exists, that is not the same as the given user, with the given user's email address
        if (findByEmail(user.getEmail()) != null && !findByEmail(user.getEmail()).equals(user)) {
            throw new IllegalArgumentException("Email is already in use! Please try again");
        }

        // Check if a user exists, that is not the same as the given user, with the given user's phone number
        if (findByPhone(user.getPhone()) != null && !findByPhone(user.getPhone()).equals(user)) {
            throw new IllegalArgumentException("Phone number is already in use! Please try again");
        }
    }
}