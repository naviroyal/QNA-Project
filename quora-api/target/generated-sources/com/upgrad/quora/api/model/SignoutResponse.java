package com.upgrad.quora.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SignOutResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-12T13:43:12.819+05:30")

public class SignOutResponse   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("message")
  private String message = null;

  public SignOutResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * uuid of the user who is signed out
   * @return id
  **/
  @ApiModelProperty(required = true, value = "uuid of the user who is signed out")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public SignOutResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * message to show the status of the signed out user
   * @return message
  **/
  @ApiModelProperty(required = true, value = "message to show the status of the signed out user")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SignOutResponse signOutResponse = (SignOutResponse) o;
    return Objects.equals(this.id, signOutResponse.id) &&
        Objects.equals(this.message, signOutResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SignOutResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

