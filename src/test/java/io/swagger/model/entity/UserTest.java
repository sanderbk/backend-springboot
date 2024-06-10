package io.swagger.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
    }

    @Test
    void newUserShouldNotBeNull() {
        Assertions.assertNotNull(user);
    }
}
