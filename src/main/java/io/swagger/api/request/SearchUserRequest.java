package io.swagger.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
public class SearchUserRequest {
    private Optional<Integer> page = Optional.empty();
    private Optional<Integer> size = Optional.empty();
    private Optional<String> username = Optional.empty();
    private Optional<String> firstname = Optional.empty();
    private Optional<String> lastname = Optional.empty();
    private Optional<String> email = Optional.empty();
    private Optional<Boolean> hasAccounts = Optional.empty();
}