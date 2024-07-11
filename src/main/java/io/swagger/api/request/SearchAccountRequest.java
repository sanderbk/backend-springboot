package io.swagger.api.request;

import io.swagger.model.enumeration.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
public class SearchAccountRequest {
    private Optional<Integer> page = Optional.empty();
    private Optional<Integer> size = Optional.empty();
    private Optional<String> id = Optional.empty();
    private Optional<String> firstname = Optional.empty();
    private Optional<String> lastname = Optional.empty();
    private Optional<String> iban = Optional.empty();
    private Optional<String> username = Optional.empty();
    private Optional<AccountType> accountType = Optional.empty();
    

}
