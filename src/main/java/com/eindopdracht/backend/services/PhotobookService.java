package com.eindopdracht.backend.services;

import com.eindopdracht.backend.models.Photo;
import com.eindopdracht.backend.models.Photobook;
import com.eindopdracht.backend.repositories.PhotoRepository;
import com.eindopdracht.backend.repositories.PhotobookRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PhotobookService {

    private final PhotobookRepository photobookRepository;
    private final PhotoRepository photoRepository;

    public PhotobookService(PhotobookRepository photobookRepository, PhotoRepository photoRepository){
        this.photobookRepository = photobookRepository;
        this.photoRepository = photoRepository;
    }

    public Photobook create(String title){
        Photobook photobook = Photobook.create(title);
        return photobookRepository.save(photobook);
    }

    public List<Photobook> list() {
        return photobookRepository.findAll();
    }

    public Photobook get(UUID id){
        return photobookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Fotoboek niet gevonden"));
    }

    public void addPhoto(UUID photobookId, UUID photoId){
        Photobook photobook = get(photobookId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(()->new RuntimeException("Foto niet gevonden"));

        photo.setPhotobook(photobook);

        int nextIndex = photobook.getPhotos() == null ? 0 : photobook.getPhotos().size();
        photo.setSortIndex(nextIndex);
    }

    public void setPhotoOrder(UUID photobookId, List<UUID> photoIds){
        Photobook photobook = get(photobookId);

        for (int i = 0; i < photoIds.size(); i++){
            UUID photoId = photoIds.get(i);
            Photo photo = photoRepository.findById(photoId)
                    .orElseThrow(()-> new RuntimeException("Foto is niet gevonden" + photoId));
            if (photo.getPhotobook() == null || !photo.getPhotobook().getId().equals(photobook.getId())){
                throw new RuntimeException("Deze foto hoort niet bij dit fotoboek:" + photoId);
            }
            photo.setSortIndex(i);
            photoRepository.save(photo);
        }
    }

    public Photobook readyForReview(UUID id){
        Photobook photobook = get(id);
        if (photobook.getStatus() != Photobook.PhotobookStatus.DESIGNING && photobook.getStatus() != Photobook.PhotobookStatus.UPLOADING){
            throw new RuntimeException("Fotoboek kan niet naar READY_FOR_REVIEW vanuit status:" + photobook.getStatus());
        }
        photobook.setStatus(Photobook.PhotobookStatus.READY_FOR_REVIEW);
        return photobookRepository.save(photobook);
    }

    public Photobook reject(UUID id, String comment){
        Photobook photobook = get(id);
        if (photobook.getStatus() != Photobook.PhotobookStatus.READY_FOR_REVIEW){
            throw new RuntimeException("Alleen fotoboeken READY_FOR_REVIEW kunnen afgekeurd worden.");
        }
        photobook.setStatus(Photobook.PhotobookStatus.REJECTED);
        photobook.setLastFeedback(comment);

        return photobookRepository.save(photobook);
    }

    public Photobook approve(UUID id){
        Photobook photobook = get(id);
        if (photobook.getStatus() != Photobook.PhotobookStatus.READY_FOR_REVIEW){
            throw new RuntimeException("Alleen fotoboeken READY_FOR_REVIEW kunnen afgekeurd worden.");
        }
        photobook.setStatus(Photobook.PhotobookStatus.REJECTED);
        return photobookRepository.save(photobook);
    }

    public Photobook sendToPrinter(UUID id){
        Photobook photobook = get(id);
        if (photobook.getStatus() != Photobook.PhotobookStatus.APPROVED){
            throw new RuntimeException("Alleen APPROVED fotoboeken kunnen naar de drukker");
        }
        photobook.setStatus(Photobook.PhotobookStatus.SENT_TO_PRINTER);
        photobook.setSentToPrinterAt(Instant.now());
        return photobookRepository.save(photobook);
    }

    public Photobook readyForPickup(UUID id){
        Photobook photobook = get(id);
        if (photobook.getStatus() != Photobook.PhotobookStatus.SENT_TO_PRINTER && photobook.getStatus() != Photobook.PhotobookStatus.PRINTED){
            throw new RuntimeException("Fotoboek kan niet klaar zijn voor afhalen vanuit status:" + photobook.getStatus());
        }
        photobook.setStatus(Photobook.PhotobookStatus.READY_FOR_PICKUP);
        return photobookRepository.save(photobook);
    }
}
