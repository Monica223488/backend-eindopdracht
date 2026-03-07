package com.eindopdracht.backend.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Photobook {

    @Id
    private UUID id;
    @Setter
    private String title;
    @Setter
    private Instant createdAt;
    @Setter
    private Instant approvedAt;
    @Setter
    private Instant sentToPrinterAt;
    @Setter
    private String lastFeedback;
    @Setter
    private int pages;

    @Enumerated(EnumType.STRING)
    @Setter
    private PhotobookStatus status;

    public enum PhotobookStatus {
        UPLOADING,
        DESIGNING,
        READY_FOR_REVIEW,
        APPROVED,
        REJECTED,
        SENT_TO_PRINTER,
        PRINTED,
        READY_FOR_PICKUP
    }

    @OneToMany(mappedBy = "photobook")
    @OrderBy("sortIndex ASC")
    private List<Photo> photos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Photobook(String title, Instant createdAt, Instant approvedAt, int pages, PhotobookStatus status) {
        this.title = title;
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
        this.pages = pages;
        this.status = status;
    }

    public static Photobook create(String title){
        Photobook photobook = new Photobook();
        photobook.title = (title == null || title.isBlank()) ? "Untitled photobook" : title;
        photobook.createdAt = Instant.now();
        photobook.status = PhotobookStatus.UPLOADING;
        return photobook;
    }

    @PrePersist
    public void generateId() {
        if (id == null){
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (status == null) {
            status = PhotobookStatus.UPLOADING;
        }
    }
}
