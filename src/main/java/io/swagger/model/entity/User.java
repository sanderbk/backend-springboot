package io.swagger.model.entity;

import io.swagger.model.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueUsernameEmailAndPhoneNr", columnNames = {"username", "email", "phone"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    //Makes sure a User can have multiple UserTypes and fills in a users UserType automatically when getting from the DB
    @ElementCollection(fetch = FetchType.EAGER)
    private List<UserType> userTypes;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;
    private String address;
    private String email;
    private String phone;
    private OffsetDateTime registeredOn;
    private Double dayLimit;
    private Double transLimit;
    private Boolean active;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;
}
