package com.yaf.who.service;

import com.yaf.who.dao.UserDAO;
import com.yaf.who.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ardafakili
 * @date 23.04.2020
 */

@Service
public class UserProfileService {

    private final UserDAO userDao;

    @Autowired
    public UserProfileService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void addUserProfile(UserProfile UserProfile) {
        userDao.insertUserProfile(UserProfile);
    }

    public List<UserProfile> getPeople() {
        return userDao.selectAllUserProfiles();
    }

    public Optional<UserProfile> getUserProfileById(UUID id){
        return userDao.selectUserProfileById(id);
    }

    public int deleteUserProfile(UUID id) {
        return userDao.deleteUserProfileWithById(id);
    }

    public int updateUserProfile(UUID id, UserProfile UserProfile) {
        return userDao.updateUserProfileById(id, UserProfile);
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        //
    }
}
