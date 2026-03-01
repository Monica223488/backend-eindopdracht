package com.eindopdracht.backend.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo {

    @Id
    private UUID id;
    @Setter
    private String name;
    @Setter
    private String originalFileName;
    @Setter
    private String contentType;
    @Setter
    private Long size;
    @Setter
    private String storageKey;
    @Setter
    private Instant uploadedAt;

    public static Photo create(String originalFileName, String contentType, long size, String storageKey) {
        return new Photo(originalFileName, contentType, size, storageKey, Instant.now());
    }

    @PrePersist
    public void generateId(){
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photobook_id")
    private Photobook photobook;

    public Photo(String originalFileName, String contentType, long size, String storageKey, Instant uploadedAt) {
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.size = size;
        this.storageKey = storageKey;
        this.uploadedAt = uploadedAt;
    }
}
