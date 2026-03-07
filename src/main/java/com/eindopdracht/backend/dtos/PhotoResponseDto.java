package com.eindopdracht.backend.dtos;

import com.eindopdracht.backend.models.Photo;

import java.time.Instant;
import java.util.UUID;

public class PhotoResponseDto {

    public UUID id;
    public String name;
    public String originalFileName;
    public String contentType;
    public Long size;
    public String storageKey;
    public Instant uploadedAt;

    public PhotoResponseDto(Photo photo){
        this.id = photo.getId();
        this.name = photo.getName();
        this.originalFileName = photo.getOriginalFileName();
        this.contentType = photo.getContentType();
        this.size = photo.getSize();
        this.storageKey = photo.getStorageKey();
        this.uploadedAt = photo.getUploadedAt();
    }
    public PhotoResponseDto() {}
}
