package com.yaf.who.service;

import com.yaf.who.bucket.BucketName;
import com.yaf.who.dao.UserDAO;
import com.yaf.who.model.Action;
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

    public void addUserProfile(String username, MultipartFile file) {


        isFileEmpty(file);
        isImage(file);
        Map<String, String> metadata = extractMetadata(file);

        UUID userId = UUID.randomUUID();
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userId);
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        UserProfile userProfile = new UserProfile(userId, username, filename);

        try {
            userDao.insertUserProfile(userProfile);
            fileStoreService.save(path, filename, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    public List<UserProfile> getPeople() {
        return userDao.selectAllUserProfiles();
    }

    public List<Action> getActions() {
        return userDao.selectAllActions();
    }

    public UserProfile getUserProfileById(UUID id) {
        return userDao.selectUserProfileById(id);
    }

    public int deleteUserProfile(UUID id) {
        return userDao.deleteUserProfileWithById(id);
    }

    public int updateUserProfile(UUID id, UserProfile UserProfile) {
        return userDao.updateUserProfileById(id, UserProfile);
    }

//    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
//
//        isFileEmpty(file);
//        isImage(file);
//        UserProfile user = getUserProfileOrThrow(userProfileId);
//        Map<String, String> metaData = extractMetaData(file);
//
//        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
//        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
//        try {
//            fileStoreService.save(path, filename, Optional.of(metaData), new ByteArrayInputStream(file.getInputStream()));
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        }
//        user.setUserImageLink(filename);
//        userDao.updateUserProfileById(userProfileId, user);
//
//    }

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

    public byte[] downloadUserActionImage(int dbid) {

        Action action = getUserActionOrThrow(dbid);

        if (action.getImage() != null && !action.getImage().isEmpty()) {
            String path = String.format(
                    "%s/%s",
                    BucketName.PROFILE_IMAGE.getBucketName(),
//                    action.getDbid()); //TODO
                      action.getUserProfile().getUserProfileId());
            return fileStoreService.download(path, action.getImage());
        }

        return null;

    }

    private Map<String, String> extractMetadata(MultipartFile file) {
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

    private Action getUserActionOrThrow(int dbid) {
        return userDao
                .selectAllActions()
                .stream()
                .filter(action -> action.getDbid()==dbid)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Action %s not found by dbid :", dbid)));
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                ContentType.IMAGE_JPEG.getMimeType(),
                ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        }
    }
    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }
}
