package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.PhotoResponseDto;
import com.eindopdracht.backend.dtos.PhotobookResponseDto;
import com.eindopdracht.backend.dtos.PhotobookCreateRequestDto;
import com.eindopdracht.backend.dtos.PhotoOrderRequestDto;
import com.eindopdracht.backend.dtos.PhotobookRejectionDto;
import com.eindopdracht.backend.models.Photobook;
import com.eindopdracht.backend.services.PhotobookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/photobooks")
public class PhotobookController {

    private final PhotobookService photobookService;

    public PhotobookController(PhotobookService photobookService){
        this.photobookService = photobookService;
    }

    @PostMapping
    public ResponseEntity<PhotobookResponseDto> create(@RequestBody PhotobookCreateRequestDto dto) {
        Photobook created = photobookService.create(dto.title);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PhotobookResponseDto(created));
    }

    @GetMapping
    public List<PhotobookResponseDto> list(){
        return photobookService.list().stream()
                .map(PhotobookResponseDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public PhotobookResponseDto get(@PathVariable UUID id){
        return new PhotobookResponseDto(photobookService.get(id));
    }

    @PostMapping("/{photobookId}/photos/{photoId}")
    public ResponseEntity<Void> addPhoto(@PathVariable UUID photobookId, @PathVariable UUID photoId) {
        photobookService.addPhoto(photobookId, photoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/photos/order")
    public ResponseEntity<Void> setPhotoOrder(@PathVariable UUID id, @RequestBody PhotoOrderRequestDto dto) {
        photobookService.setPhotoOrder(id, dto.photoIds);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ready-for-review")
    public PhotobookResponseDto readyForReview(@PathVariable UUID id){
        return new PhotobookResponseDto(photobookService.readyForReview(id));
    }

    @PatchMapping("/{id}/approve")
    public PhotobookResponseDto approve(@PathVariable UUID id){
        return new PhotobookResponseDto(photobookService.approve(id));
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('CUSTOMER')")
    public PhotobookResponseDto reject(@PathVariable UUID id, @RequestBody(required = false) PhotobookRejectionDto dto){
        String comment = dto == null ? null : dto.comment;
        return new PhotobookResponseDto(photobookService.reject(id, comment));

    }

    @PatchMapping("/{id}/send-to-printer")
    @PreAuthorize("hasRole('DESIGNER')")
    public PhotobookResponseDto sendToPrinter(@PathVariable UUID id){
            return new PhotobookResponseDto(photobookService.sendToPrinter(id));
    }

    @PatchMapping("/{id}/ready-for-pickup")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public PhotobookResponseDto readyForPickup(@PathVariable UUID id){
        return new PhotobookResponseDto(photobookService.readyForPickup(id));
    }
}
