package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.PhotoRequestDto;
import com.eindopdracht.backend.dtos.PhotoResponseDto;
import com.eindopdracht.backend.models.Photo;
import com.eindopdracht.backend.services.PhotoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.util.UUID;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService service) {
        this.photoService = service;
    }

    @PostMapping
    public ResponseEntity<PhotoResponseDto> upload(@RequestParam("file") MultipartFile file) {
        Photo saved = photoService.upload(file);
        PhotoResponseDto dto = new PhotoResponseDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> view(@PathVariable UUID id){

            Photo meta = photoService.getMeta(id);
            Resource resource = photoService.loadAsResource(meta.getStorageKey());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, meta.getContentType())
                    .body(resource);
        }



        @GetMapping("/{id}/download")
        public ResponseEntity<Resource> download(@PathVariable UUID id){

            Photo meta = photoService.getMeta(id);
            Resource resource = photoService.loadAsResource(meta.getStorageKey());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, meta.getContentType())
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + meta.getOriginalFileName() + "\"")
                    .body(resource);
        }



        @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        photoService.delete(id);
        return ResponseEntity.noContent().build();
        }


    }
