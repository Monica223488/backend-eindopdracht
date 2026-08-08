package com.eindopdracht.backend.services;

import com.eindopdracht.backend.exceptions.BadRequestException;
import com.eindopdracht.backend.exceptions.ResourceNotFoundException;
import com.eindopdracht.backend.models.Photo;
import com.eindopdracht.backend.repositories.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoServiceTest {

    @Mock
    private PhotoRepository photoRepository;

    @TempDir
    Path tempDir;

    private PhotoService photoService;

    @BeforeEach
    void setUp(){
        photoService = new PhotoService(photoRepository, tempDir.toString());
    }

    @Test
    void constructor_shouldCreateStorageDirectory() {
        Path newDir = tempDir.resolve("photos-subdir");

        PhotoService service = new PhotoService(photoRepository, newDir.toString());

        assertTrue(Files.exists(newDir));
        assertTrue(Files.isDirectory(newDir));
    }

    @Test
    void constructor_shouldThrowRuntimeExeption_whenStorageDirectoryCannotBeCreated() throws IOException {
        Path file = tempDir.resolve("already-a-file.txt");
        Files.writeString(file, "test");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                ()-> new PhotoService(photoRepository, file.toString())
        );
        assertEquals("Kan storage map niet maken", ex.getMessage());
        assertNotNull(ex.getCause());
    }

    @Test
    void upload_shouldThrowBadRequest_whenFileIsEmpty() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "empty.jpg", "image/jpeg", new byte[0]
        );

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> photoService.upload(file)
        );

        assertEquals("Leeg bestand", ex.getMessage());
        verify(photoRepository, never()).save(any());
    }

    @Test
    void upload_shouldThrowBadRequest_whenContentTypeIsNotImage() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "abc".getBytes()
        );

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> photoService.upload(file)
        );

        assertEquals("Alleen afbeeldingen toegestaan", ex.getMessage());
        verify(photoRepository, never()).save(any());
    }

    @Test
    void upload_shouldSavePngExtension_whenPngIsValid() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.png", "image/png", "abc".getBytes()
        );

        when(photoRepository.save(any(Photo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Photo result = photoService.upload(file);

        assertTrue(result.getStorageKey().endsWith(".png"));
    }

    @Test
    void upload_shouldUseNoExtension_whenImageTypeIsOther() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.gif", "image/gif", "abc".getBytes()
        );

        when(photoRepository.save(any(Photo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Photo result = photoService.upload(file);

        assertFalse(result.getStorageKey().contains("."));
    }

    @Test
    void upload_shouldSavePhotoAndFile_whenJpegIsValid() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "abc".getBytes()
        );

        when(photoRepository.save(any(Photo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Photo result = photoService.upload(file);

        assertNotNull(result);
        assertEquals("test.jpg", result.getOriginalFileName());
        assertEquals("image/jpeg", result.getContentType());
        assertEquals(3, result.getSize());
        assertTrue(result.getStorageKey().endsWith(".jpg"));
        assertTrue(Files.exists(tempDir.resolve(result.getStorageKey())));

        verify(photoRepository).save(any(Photo.class));
    }

    @Test
    void delete_shouldThrowRuntimeException_whenFileCannotBeDeleted() throws IOException {
        UUID id = UUID.randomUUID();

        Path directory = tempDir.resolve("folder");
        Files.createDirectory(directory);
        Files.writeString(directory.resolve("file.txt"), "abc");

        Photo photo = mock(Photo.class);
        when(photo.getStorageKey()).thenReturn("folder");
        when(photoRepository.findById(id)).thenReturn(Optional.of(photo));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> photoService.delete(id)
        );

        assertEquals("Niet gelukt om bestand te verwijderen", ex.getMessage());
        verify(photoRepository, never()).delete(photo);
    }
    @Test
    void upload_shouldThrowRuntimeException_whenInputStreamFails() throws IOException {
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getInputStream()).thenThrow(new IOException("read failed"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> photoService.upload(file)
        );

        assertEquals("Opslaan op disk mislukt", ex.getMessage());
        verify(photoRepository, never()).save(any());
    }

    @Test
    void getMeta_shouldReturnPhoto_whenIdExists() {
        UUID id = UUID.randomUUID();
        Photo photo = mock(Photo.class);

        when(photoRepository.findById(id)).thenReturn(Optional.of(photo));

        Photo result = photoService.getMeta(id);

        assertSame(photo, result);
        verify(photoRepository).findById(id);
    }


    @Test
    void loadAsResource_shouldReturnResource_whenFileExists() throws IOException {
        String storageKey = "photo.jpg";
        Path file = tempDir.resolve(storageKey);
        Files.writeString(file, "abc");

        Resource resource = photoService.loadAsResource(storageKey);

        assertNotNull(resource);
        assertTrue(resource.exists());
        assertEquals(file.toUri(), resource.getURI());
    }

    @Test
    void loadAsResource_shouldThrowRuntimeException_whenFileDoesNotExist() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> photoService.loadAsResource("missing.jpg")
        );

        assertEquals("Het bestand is niet gevonden", ex.getMessage());
        assertNotNull(ex.getCause());
    }

    @Test
    void loadAsResource_shouldThrowRuntimeException_whenPathTraversalIsUsed() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> photoService.loadAsResource("../secret.txt")
        );

        assertEquals("Het bestand is niet gevonden", ex.getMessage());
        assertNotNull(ex.getCause());
        assertTrue(ex.getCause() instanceof BadRequestException);
    }

    @Test
    void delete_shouldRemoveFileAndEntity_whenPhotoExists() throws IOException {
        UUID id = UUID.randomUUID();
        String storageKey = "to-delete.jpg";

        Path file = tempDir.resolve(storageKey);
        Files.writeString(file, "abc");

        Photo photo = mock(Photo.class);
        when(photo.getStorageKey()).thenReturn(storageKey);
        when(photoRepository.findById(id)).thenReturn(Optional.of(photo));

        photoService.delete(id);

        assertFalse(Files.exists(file));
        verify(photoRepository).delete(photo);
    }

    @Test
    void delete_shouldThrowNotFound_whenPhotoDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(photoRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> photoService.delete(id)
        );

        assertEquals("De foto is niet gevonden", ex.getMessage());
        verify(photoRepository, never()).delete(any());
    }

}