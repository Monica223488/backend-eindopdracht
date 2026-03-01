package com.eindopdracht.backend.services;

import com.eindopdracht.backend.models.Photo;
import com.eindopdracht.backend.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final Path root;

    public PhotoService(PhotoRepository photoRepository,
                        @Value("${photo.storage.dir:./data/photos}") String storageDir) {
        this.photoRepository = photoRepository;
        this.root = Paths.get(storageDir).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.root);
        } catch (IOException e){
            throw new RuntimeException("Kan storage map niet maken", e);
        }
    }

    public Photo upload(MultipartFile file) {
        if (file.isEmpty()) throw new RuntimeException("Leeg bestand");

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Alleen afbeeldingen toegestaan");
        }

        UUID id = UUID.randomUUID();
        String ext = switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            default -> "";
        };

        String storageKey = id + ext;

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, root.resolve(storageKey), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Opslaan op disk mislukt", e);
        }

        Photo photo = Photo.create(
                file.getOriginalFilename(),
                contentType,
                file.getSize(),
                storageKey
        );

        return photoRepository.save(photo);
    }

    public Photo getMeta(UUID id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto niet gevonden"));
    }

    public Resource loadAsResource(String storageKey) {
        try {
            Path path = root.resolve(storageKey).normalize();
            if (!path.startsWith(root)) throw new RuntimeException ("Ongeldig pad");
            Resource res = new UrlResource(path.toUri());
            if (!res.exists()) throw new RuntimeException("Bestand niet gevonden");
            return res;
        } catch (Exception e){
            throw new RuntimeException("Bestand niet gevonden", e);
        }
    }

    public void delete(UUID id) {
        Photo photo = getMeta(id);

        try{
            Files.deleteIfExists(root.resolve(photo.getStorageKey()).normalize());

        } catch (IOException e){
            throw new RuntimeException("Kon bestand niet verwijderen", e);
        }
        photoRepository.delete(photo);
    }
}
