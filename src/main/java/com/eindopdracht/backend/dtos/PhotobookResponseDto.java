package com.eindopdracht.backend.dtos;

import com.eindopdracht.backend.models.Photobook;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class PhotobookResponseDto {
    public UUID id;
    public String title;
    public Photobook.PhotobookStatus status;
    public Instant createdAt;
    public Instant approvedAt;

    public List<PhotoItemDto> photos;

    public PhotobookResponseDto(Photobook photobook) {
        this.id = photobook.getId();
        this.title = photobook.getTitle();
        this.status = photobook.getStatus();
        this.createdAt = photobook.getCreatedAt();
        this.approvedAt = photobook.getApprovedAt();

        this.photos = photobook.getPhotos().stream()
                .map(PhotoItemDto::new)
                .toList();
    }

    public static class PhotoItemDto {
        public UUID id;
        public String originalFileName;
        public String contentType;
        public Long size;
        public Integer sortIndex;
        public String url;

        public PhotoItemDto(com.eindopdracht.backend.models.Photo photo) {
            this.id = photo.getId();
            this.originalFileName = photo.getOriginalFileName();
            this.contentType = photo.getContentType();
            this.size = photo.getSize();
            this.sortIndex = photo.getSortIndex();
            this.url = "/photos/" + photo.getId();
        }
    }
}
