package com.yaf.who.controller;

import com.yaf.who.model.UserProfile;
import com.yaf.who.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * @author ardafakili
 * @date 24.04.2020
 */

@RequestMapping("add")
@RestController
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("user-profile/{username}")
    public void addUserProfile(@PathVariable("username") String username) {
        userProfileService.addUserProfile(username);
    }

    @GetMapping
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getPeople();
    }

//    @PostMapping(
//            path = "{userProfileId}/image/upload",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public void uploadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId,
//                                       @RequestParam("file") MultipartFile file) {
//        userProfileService.uploadUserProfileImage(userProfileId, file);
//    }

    @GetMapping("{userProfileId}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId) {
        return userProfileService.downloadUserProfileImage(userProfileId);
    }

    @PostMapping("temp-image-upload")
    public void uploadTempImage(@RequestParam("file") MultipartFile file) {
        userProfileService.uploadTempProfileImage(file);
    }

    @GetMapping("temp-image-download")
    public byte[] downloadTempImage() {
       return userProfileService.downloadTempImage();
    }

}
