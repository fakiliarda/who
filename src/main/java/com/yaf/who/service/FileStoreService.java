package com.yaf.who.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

/**
 * @author ardafakili
 * @date 23.04.2020
 */

@Service
public class FileStoreService {

    private final AmazonS3 s3;

    @Autowired
    public FileStoreService(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path,
                     String fileName,
                     Optional<Map<String, String>> optionalMetaData,
                     InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });
        try {
            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file to s3", e);
        }

    }
}
