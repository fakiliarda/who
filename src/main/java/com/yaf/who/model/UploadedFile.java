package com.yaf.who.model;

import lombok.Getter;

/**
 * @author ardafakili
 * @date 26.04.2020
 */
public class UploadedFile {
    @Getter
    private final String contentType;
    @Getter
    private final String originalFilename;
    private final byte[] data;

    public UploadedFile(String contentType, String fileName, byte[] data) {
        this.contentType = contentType;
        this.originalFilename = fileName;
        this.data = data.clone();
    }

    public byte[] getData() {
        return data.clone();
    }

    public int getSize() {
        return data.length;
    }
}
