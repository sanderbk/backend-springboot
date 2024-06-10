package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.entity.User;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public ResponseEntity<UserDTO> updateUser(@Parameter(in = ParameterIn.PATH, description = "Username input", required = true, schema = @Schema()) @PathVariable("username") String username, @Parameter(in = ParameterIn.DEFAULT, description = "Updated user object", required = true, schema = @Schema()) @Valid @RequestBody UserDTO body) {

        User user = mapper.map(body, User.class);

        try {
            // Check if a user exist with the given user's username, email or phone number
            userService.doesUserDataExist(user);

            user = userService.updateUser(user);

            UserDTO response = mapper.map(user, UserDTO.class);
            return new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException | NoSuchElementException ex) {
            // If the service throws an Exception, throw a ResponseStatusException to provide the frontend with the right HTTP Status Code & Error Message
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> getByEmail(@Parameter(in = ParameterIn.PATH, description = "Email input", required = true, schema = @Schema()) @PathVariable("email") String email) {
        try {
            UserDTO response = mapper.map(userService.findByEmail(email), UserDTO.class);
            return new ResponseEntity<UserDTO>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with given email not found");
        }

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> getByUsername(@Parameter(in = ParameterIn.PATH, description = "Username input", required = true, schema = @Schema()) @PathVariable("username") String username) {
        try {
            UserDTO response = mapper.map(userService.findByUsername(username), UserDTO.class);

            return new ResponseEntity<UserDTO>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with given username not found");
        }

    }

    // The getAll type methods will always return a List<User> with at least 1 element, because a token is needed for these endpoints and an existing User needs to log in.
    // There is also a standard User (bank).

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserDTO>> getAllUsers(@Min(0) @Parameter(in = ParameterIn.QUERY, description = "Number of records to skip for pagination", schema = @Schema(allowableValues = {})) @Valid @RequestParam(value = "skip", required = false) Integer skip, @Min(1) @Max(200000) @Parameter(in = ParameterIn.QUERY, description = "Maximum number of records to return", schema = @Schema(allowableValues = {}, minimum = "1", maximum = "200000")) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        List<UserDTO> dtos = userService.getAll(skip, limit)
                .stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());

        return new ResponseEntity<List<UserDTO>>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserDTO>> getAllUsersWithoutAccount() {

        List<UserDTO> dtos = userService.getAllWithoutAccount()
                .stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());

        return new ResponseEntity<List<UserDTO>>(dtos, HttpStatus.OK);
    }
}
