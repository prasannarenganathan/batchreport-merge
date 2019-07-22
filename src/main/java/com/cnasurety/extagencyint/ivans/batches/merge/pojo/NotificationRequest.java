package com.cnasurety.extagencyint.ivans.batches.merge.pojo;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* Request
*/

@Validated
public class NotificationRequest   {
	
  @JsonProperty("notificationGlobalId")
  private String notificationGlobalId = null;

  public NotificationRequest notificationGlobalId(String notificationGlobalId) {
    this.notificationGlobalId = notificationGlobalId;
    return this;
  }

 

  /**
   * Get notificationGlobalId
   * @return notificationGlobalId
  **/

 // @ApiModelProperty(required = true, value = "")
  @NotNull
  public String getNotificationGlobalId() {
    return notificationGlobalId;
  }


  public void setNotificationGlobalId(String notificationGlobalId) {
    this.notificationGlobalId = notificationGlobalId;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotificationRequest request = (NotificationRequest) o;
    return Objects.equals(this.notificationGlobalId, request.notificationGlobalId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(notificationGlobalId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    sb.append("    \"notificationGlobalId\": ").append("\"").append(toIndentedString(notificationGlobalId)).append("\"").append("\n");
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