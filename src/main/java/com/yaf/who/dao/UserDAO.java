package com.yaf.who.dao;

import com.yaf.who.model.Action;
import com.yaf.who.model.UserProfile;

import java.util.List;
import java.util.UUID;

/**
 * @author ardafakili
 * @date 23.04.2020
 */
public interface UserDAO {

    UserProfile insertUserProfile(UserProfile userProfile);

    List<UserProfile> selectAllUserProfiles();

    UserProfile selectUserProfileById(UUID id);

    int deleteUserProfileWithById(UUID id);

    int updateUserProfileById(UUID id, UserProfile userProfile);

    List<Action> selectAllActions();

}
