package com.last.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("profile_image_url")
    private String profile_image_url;

    @JsonProperty("email")
    private String email;

    @JsonProperty("accessToken")
    private String accessToken;

    // getters and setters
    public String getProfileNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profile_image_url;
    }

    public String getAccountEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }
}