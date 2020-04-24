package com.yaf.who.bucket;

import lombok.Getter;

/**
 * @author ardafakili
 * @date 23.04.2020
 */
public enum BucketName {

    PROFILE_IMAGE("who-bucket-v1");

    @Getter
    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
