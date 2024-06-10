package io.swagger.model.dto;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.enumeration.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;
import java.util.UUID;

/**
 * Request body for login responses with JWT
 */
@Schema(description = "Request body for login responses with JWT")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-18T15:53:51.610Z[GMT]")


public class TokenDTO   {
  @JsonProperty("token")
  private String token = null;
  @JsonProperty("username")
  private String username = null;
  @JsonProperty("userID")
  private UUID userId = null;
  @JsonProperty("userrole")
  private List<UserType> userrole = null;

  public TokenDTO token(String token) {
    this.token = token;
    return this;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  /**
   * Get token
   * @return token
   **/
  @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", description = "")
  
    public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
  public void setUserName(String username) {
    this.username = username;
  }
  public void setUserrole(List<UserType> userrole) {
    this.userrole = userrole;
  }

  public List<UserType> getUserrole() {
    return userrole;
  }
  public String getUsername() {
    return username;
  }





  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenDTO tokenDTO = (TokenDTO) o;
    return Objects.equals(this.token, tokenDTO.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenDTO {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
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
