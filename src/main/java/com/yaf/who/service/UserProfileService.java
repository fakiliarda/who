package com.yaf.who.service;

import com.yaf.who.bucket.BucketName;
import com.yaf.who.dao.UserDAO;
import com.yaf.who.model.UploadedFile;
import com.yaf.who.model.UserProfile;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
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
    private UploadedFile uploadedFile;
    private boolean profileImageDisplayed = false;

    @Autowired
    public UserProfileService(UserDAO userDao, FileStoreService fileStoreService) {
        this.userDao = userDao;
        this.fileStoreService = fileStoreService;
    }

    public void addUserProfile(String username) {
        if (uploadedFile != null) {
            UserProfile userProfile = userDao.insertUserProfile(username);
            uploadUserProfileImage(userProfile.getUserProfileId(), this.uploadedFile);
        }
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

    public void uploadUserProfileImage(UUID userProfileId, UploadedFile file) {

        isFileEmpty(file.getData());
        isImage(file.getContentType());
        UserProfile user = getUserProfileOrThrow(userProfileId);
        Map<String, String> metaData = extractMetaData(file);

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        fileStoreService.save(path, filename, Optional.of(metaData), new ByteArrayInputStream(file.getData()));
        user.setUserImageLink(filename);
        userDao.updateUserProfileById(userProfileId, user);

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

    public byte[] downloadTempImage() {
        if (this.uploadedFile != null && !profileImageDisplayed) {
            profileImageDisplayed = true;
            return uploadedFile.getData();
        }
        profileImageDisplayed = false;
        return fileStoreService.download(BucketName.PROFILE_IMAGE.getBucketName(), "profile.png");
    }

    public void uploadTempProfileImage(MultipartFile file) {
        try {
            this.uploadedFile = new UploadedFile(file.getContentType(), file.getOriginalFilename(), file.getBytes());
            profileImageDisplayed = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> extractMetaData(UploadedFile file) {
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

    private void isImage(String contentType) {
        if (Arrays.asList(
                ContentType.IMAGE_JPEG,
                ContentType.IMAGE_PNG).
                contains(contentType)) {
            throw new IllegalStateException("File must be an image.");
        }
    }

    private void isFileEmpty(byte[] fileData) {
        if (fileData.length <= 0) {
            throw new IllegalStateException("Cannot upload empty file.");
        }
    }
}
