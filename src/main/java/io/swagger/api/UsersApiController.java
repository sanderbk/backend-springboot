package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.api.request.PutUserLimitRequest;
import io.swagger.api.request.SearchUserRequest;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.entity.User;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-23T13:04:25.984Z[GMT]")
@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = {"Employee", "Customer"})
public class UsersApiController implements UsersApi {

    @Autowired
    private UserService userService;

    private ModelMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.mapper = new ModelMapper();
        this.request = request;
    }

    // No role is needed for this endpoint, since there isn't a token yet.
    public ResponseEntity<UserDTO> addUser(@Parameter(in = ParameterIn.DEFAULT, description = "New user object", required = true, schema = @Schema()) @Valid @RequestBody UserDTO body) {

        try {
            // Map the UserDTO object from the body to a new User object
            User user = mapper.map(body, User.class);

            // Check if a user exist with the given user's username, email or phone number
            userService.doesUserDataExist(user);

            // Add the user to the DB
            user = userService.addUser(user);

            // Respond with the new User, mapped to a UserDTO object
            UserDTO response = mapper.map(user, UserDTO.class);
            return new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException | NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(in = ParameterIn.PATH, description = "Username input", required = true, schema = @Schema()) @PathVariable("username")
            String username, @Parameter(in = ParameterIn.DEFAULT, description = "Updated user object", required = true, schema = @Schema())
            @RequestBody PutUserLimitRequest userLimitRequest) {

        User foundUser = userService.findByUsername(username);

        foundUser.setDayLimit(userLimitRequest.getDayLimit());
        foundUser.setTransLimit(userLimitRequest.getTransLimit());

        try {

            User user = userService.updateUser(foundUser);

            UserDTO response = mapper.map(user, UserDTO.class);

            return new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException | NoSuchElementException ex) {
            // If the service throws an Exception, throw a ResponseStatusException to provide the frontend with the right HTTP Status Code & Error Message
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(in = ParameterIn.PATH, description = "User ID", required = true, schema = @Schema())
            @PathVariable("id") UUID id) {
        try {
            User user = userService.findById(id);
            UserDTO response = mapper.map(user, UserDTO.class);
            return new ResponseEntity<UserDTO>(response, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with given ID not found");
        }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<UserDTO> getByUsername(@Parameter(in = ParameterIn.PATH, description = "Username input", required = true, schema = @Schema()) @PathVariable("username") String username) {
        try {
            UserDTO response = mapper.map(userService.findByUsername(username), UserDTO.class);

            return new ResponseEntity<UserDTO>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with given username not found");
        }

    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<Page<UserDTO>> searchUsers(@Parameter(description = "Search criteria for users") @Valid SearchUserRequest searchUserRequest) {
        Page<User> userPage = userService.getAllFiltered(searchUserRequest);
        Page<UserDTO> dtos = userPage.map(user -> mapper.map(user, UserDTO.class));
        return ResponseEntity.ok(dtos);
    }
}
