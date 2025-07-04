package io.swagger.service;

import io.swagger.api.request.SearchUserRequest;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.dto.TokenDTO;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.UserStatus;
import io.swagger.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            // Step 1: Authenticate credentials
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            // Step 2: Find user
            User user = this.findByUsername(username);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username and/or password");
            }

            // Step 3: Check user status
            if (user.getUserStatus() == UserStatus.CLOSED) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your account is closed. Please contact support.");
            } else if (user.getUserStatus() == UserStatus.PENDING) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your account is pending approval.");
            }

            // Step 4: Generate token
            tokenDto.setToken(provider.createToken(username, user.getUserTypes()));  // Token creation supports multiple userTypes
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
                () -> new NoSuchElementException("Something went wrong; the server couldn't respond with new User object"));
    }

    public User updateUser(User updatedUser) {

        // Check if User with given id exists before updating
        if (this.findById(updatedUser.getId()) == null) {
            throw new IllegalArgumentException("The user with the requested ID" + " (" + updatedUser.getId() + ") " + "could not be updated; user does not exist");
        }

        updatedUser.setPassword(updatedUser.getPassword());

        return Optional.of(userRepo.save(updatedUser)).orElseThrow(
                () -> new NoSuchElementException("Something went wrong; the server couldn't respond with new User object"));
    }

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

    public Page<User> getAllFiltered(SearchUserRequest searchUserRequest) {
        var qryPage = searchUserRequest.getPage().orElse(0);
        var qrySize = searchUserRequest.getSize().orElse(50);
        Pageable pageable = PageRequest.of(qryPage, qrySize);

        var qryUsername = searchUserRequest.getUsername().orElse("");
        var qryFirstname = searchUserRequest.getFirstname().orElse("");
        var qryLastname = searchUserRequest.getLastname().orElse("");
        var qryEmail = searchUserRequest.getEmail().orElse("");
        var qryHasAccounts = searchUserRequest.getHasAccounts().orElse(null);

        return userRepo.findUsers(qryUsername, qryFirstname, qryLastname, qryEmail, qryHasAccounts, pageable);
    }
}