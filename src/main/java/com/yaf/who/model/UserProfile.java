package com.yaf.who.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

/**
 * @author ardafakili
 * @date 23.04.2020
 */
public class UserProfile {

    @Getter
    @Setter
    private final UUID userProfileId;

    @Getter
    @Setter
    private String username;

    @Getter()
    @Setter
    private String userImageLink; //S3 key

    public UserProfile(UUID userProfileId, String username, String userImageLink) {
        this.userProfileId = userProfileId;
        this.username = username;
        this.userImageLink = userImageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(userProfileId, that.userProfileId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(userImageLink, that.userImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfileId, username, userImageLink);
    }
}
