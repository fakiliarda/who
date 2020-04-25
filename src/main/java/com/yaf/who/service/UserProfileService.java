package com.yaf.who.service;

import com.yaf.who.bucket.BucketName;
import com.yaf.who.dao.UserDAO;
import com.yaf.who.model.UserProfile;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author ardafakili
 * @date 23.04.2020
 */

@Service
public class UserProfileService {

    private final UserDAO userDao;
    private final FileStoreService fileStoreService;

    @Autowired
    public UserProfileService(UserDAO userDao, FileStoreService fileStoreService) {
        this.userDao = userDao;
        this.fileStoreService = fileStoreService;
    }

    public void addUserProfile(UserProfile UserProfile) {
        userDao.insertUserProfile(UserProfile);
    }

    public List<UserProfile> getPeople() {
        return userDao.selectAllUserProfiles();
    }

    public Optional<UserProfile> getUserProfileById(UUID id) {
        return userDao.selectUserProfileById(id);
    }

    public int deleteUserProfile(UUID id) {
        return userDao.deleteUserProfileWithById(id);
    }

    public int updateUserProfile(UUID id, UserProfile UserProfile) {
        return userDao.updateUserProfileById(id, UserProfile);
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {

        isFileEmpty(file);
        isImage(file);
        UserProfile user = getUserProfileOrThrow(userProfileId);
        Map<String, String> metaData = extractMetaData(file);

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            fileStoreService.save(path, filename, Optional.of(metaData), file.getInputStream());
            user.setUserImageLink(filename);
            userDao.updateUserProfileById(userProfileId, user);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {

        UserProfile userProfile = getUserProfileOrThrow(userProfileId);

        if (userProfile.getUserImageLink() != null && !userProfile.getUserImageLink().isEmpty()) {
            String path = String.format(
                    "%s/%s",
                    BucketName.PROFILE_IMAGE.getBucketName(),
                    userProfile.getUserProfileId());

            return fileStoreService.download(path, userProfile.getUserImageLink());
        }

        return null;

    }

    private Map<String, String> extractMetaData(MultipartFile file) {
        Map<String, String> metaData = new HashMap<>();
        metaData.put("Content-Type", file.getContentType());
        metaData.put("Content-Length", String.valueOf(file.getSize()));
        return metaData;
    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        return userDao
                .selectAllUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found.", userProfileId)));
    }

    private void isImage(MultipartFile file) {
        if (Arrays.asList(
                ContentType.IMAGE_JPEG,
                ContentType.IMAGE_PNG).
                contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image.");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }

}
