package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.enumeration.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * UserDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-29T11:11:46.904Z[GMT]")


public class UserDTO {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("userTypes")
    @Valid
    private List<UserType> userTypes = null;

    @JsonProperty("username")
    private String username = null;

    @JsonProperty("password")
    private String password = null;

    @JsonProperty("firstname")
    private String firstname = null;

    @JsonProperty("lastname")
    private String lastname = null;

    @JsonProperty("dob")
    private LocalDate dob = null;

    @JsonProperty("address")
    private String address = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("phone")
    private String phone = null;

    @JsonProperty("registeredOn")
    private OffsetDateTime registeredOn = null;

    @JsonProperty("dayLimit")
    private Double dayLimit = null;

    @JsonProperty("transLimit")
    private Double transLimit = null;

    @JsonProperty("active")
    private Boolean active = null;

    public UserDTO id(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", description = "")

    @Valid
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserDTO userTypes(List<UserType> userTypes) {
        this.userTypes = userTypes;
        return this;
    }

    public UserDTO addUserTypesItem(UserType userTypesItem) {
        if (this.userTypes == null) {
            this.userTypes = new ArrayList<UserType>();
        }
        this.userTypes.add(userTypesItem);
        return this;
    }

    /**
     * Get userTypes
     *
     * @return userTypes
     **/
    @Schema(description = "")
    @Valid
    public List<UserType> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(List<UserType> userTypes) {
        this.userTypes = userTypes;
    }

    public UserDTO username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Get username
     *
     * @return username
     **/
    @Schema(example = "FluffyUnicorn77", required = true, description = "")
    @NotNull

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDTO password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get password
     *
     * @return password
     **/
    @Schema(example = "SeCrEt!334", description = "")

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    /**
     * Get firstname
     *
     * @return firstname
     **/
    @Schema(example = "John", required = true, description = "")
    @NotNull

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public UserDTO lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    /**
     * Get lastname
     *
     * @return lastname
     **/
    @Schema(example = "Doe", required = true, description = "")
    @NotNull

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserDTO dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    /**
     * Get dob
     *
     * @return dob
     **/
    @Schema(example = "Tue Apr 24 00:00:00 GMT 1956", required = true, description = "")
    @NotNull

    @Valid
    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public UserDTO address(String address) {
        this.address = address;
        return this;
    }

    /**
     * Get address
     *
     * @return address
     **/
    @Schema(example = "Wolkenweg 15 8324AD Haarlem", required = true, description = "")
    @NotNull

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserDTO email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     *
     * @return email
     **/
    @Schema(example = "johndoe@example.com", required = true, description = "")
    @NotNull

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO phone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Get phone
     *
     * @return phone
     **/
    @Schema(example = "+31 0634534565", required = true, description = "")
    @NotNull

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserDTO registeredOn(OffsetDateTime registeredOn) {
        this.registeredOn = registeredOn;
        return this;
    }

    /**
     * Get registeredOn
     *
     * @return registeredOn
     **/
    @Schema(description = "")

    @Valid
    public OffsetDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(OffsetDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public UserDTO dayLimit(Double dayLimit) {
        this.dayLimit = dayLimit;
        return this;
    }

    /**
     * Get dayLimit
     *
     * @return dayLimit
     **/
    @Schema(example = "5000", description = "")

    public Double getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Double dayLimit) {
        this.dayLimit = dayLimit;
    }

    public UserDTO transLimit(Double transLimit) {
        this.transLimit = transLimit;
        return this;
    }

    /**
     * Get transLimit
     *
     * @return transLimit
     **/
    @Schema(example = "2000", description = "")

    public Double getTransLimit() {
        return transLimit;
    }

    public void setTransLimit(Double transLimit) {
        this.transLimit = transLimit;
    }

    public UserDTO active(Boolean active) {
        this.active = active;
        return this;
    }

    /**
     * Get active
     *
     * @return active
     **/
    @Schema(description = "")

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(this.id, userDTO.id) &&
                Objects.equals(this.userTypes, userDTO.userTypes) &&
                Objects.equals(this.username, userDTO.username) &&
                Objects.equals(this.password, userDTO.password) &&
                Objects.equals(this.firstname, userDTO.firstname) &&
                Objects.equals(this.lastname, userDTO.lastname) &&
                Objects.equals(this.dob, userDTO.dob) &&
                Objects.equals(this.address, userDTO.address) &&
                Objects.equals(this.email, userDTO.email) &&
                Objects.equals(this.phone, userDTO.phone) &&
                Objects.equals(this.registeredOn, userDTO.registeredOn) &&
                Objects.equals(this.dayLimit, userDTO.dayLimit) &&
                Objects.equals(this.transLimit, userDTO.transLimit) &&
                Objects.equals(this.active, userDTO.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userTypes, username, password, firstname, lastname, dob, address, email, phone, registeredOn, dayLimit, transLimit, active);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    userTypes: ").append(toIndentedString(userTypes)).append("\n");
        sb.append("    username: ").append(toIndentedString(username)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    firstname: ").append(toIndentedString(firstname)).append("\n");
        sb.append("    lastname: ").append(toIndentedString(lastname)).append("\n");
        sb.append("    dob: ").append(toIndentedString(dob)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
        sb.append("    registeredOn: ").append(toIndentedString(registeredOn)).append("\n");
        sb.append("    dayLimit: ").append(toIndentedString(dayLimit)).append("\n");
        sb.append("    transLimit: ").append(toIndentedString(transLimit)).append("\n");
        sb.append("    active: ").append(toIndentedString(active)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
