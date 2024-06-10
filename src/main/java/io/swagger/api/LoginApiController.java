package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.dto.LoginDTO;
import io.swagger.model.dto.TokenDTO;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-23T13:04:25.984Z[GMT]")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = {"Employee", "Customer"})
public class LoginApiController implements LoginApi {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }


    public ResponseEntity<TokenDTO> login(@Parameter(in = ParameterIn.DEFAULT, description = "Object with username and password to compare to existing data in DB", required=true, schema=@Schema()) @Valid @RequestBody LoginDTO body) {

        // Get token from UserService


        String username = body.getUsername();
        String password = body.getPassword();



        // Create TokenDTO to respond with
        TokenDTO response = userService.login(username, password);
       // String[] list = new String[];
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("token", response.getToken());
//            obj.put("username", response.getUsername());
//            obj.put("userrole", response.getUserrole());
//        }
//        catch (JSONException e) {
//
//        }


        return new ResponseEntity<TokenDTO>(response, HttpStatus.OK);
    }

}
