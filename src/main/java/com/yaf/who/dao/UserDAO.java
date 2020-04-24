package com.yaf.who.dao;

import com.yaf.who.model.UserProfile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ardafakili
 * @date 23.04.2020
 */
public interface UserDAO {

    int insertUserProfile(UUID id, UserProfile userProfile);

    default int insertUserProfile(UserProfile userProfile) {
        UUID id = UUID.randomUUID();
        return insertUserProfile(id, userProfile);
    }

    List<UserProfile> selectAllUserProfiles();

    Optional<UserProfile> selectUserProfileById(UUID id);

    int deleteUserProfileWithById(UUID id);

    int updateUserProfileById(UUID id, UserProfile userProfile);

}
