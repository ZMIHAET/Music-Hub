package ru.kashigin.musichub.service.impls;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

import io.minio.errors.MinioException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kashigin.musichub.dto.ArtistDto;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.repository.ArtistRepository;
import ru.kashigin.musichub.service.ArtistService;
import ru.kashigin.musichub.service.mappers.ArtistMapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final MinioClient minioClient;

    @Value("${bucket.name}")
    private String bucketName;

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public List<ArtistDto> getAllArtistsApi() {
        return artistRepository.findAll().stream()
                .map(this::convertToArtistDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    @Override
    public Optional<ArtistDto> getArtistByIdApi(Long id) {
        return artistRepository.findById(id)
                .map(this::convertToArtistDto);
    }

    @Override
    public void createArtist(Artist artist, MultipartFile photo) {
        if (photo.isEmpty())
            throw new RuntimeException("Photo was not upload");
        String photoName = UUID.randomUUID() + "-" + Objects.requireNonNull(photo.getOriginalFilename());

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)                       // Название бакета
                            .object(photoName)                        // Имя файла
                            .stream(photo.getInputStream(), photo.getSize(), -1)  // Входной поток и размер файла
                            .contentType(photo.getContentType())       // MIME-тип файла
                            .build()
            );
            log.info("Файл успешно загружен в MinIO с именем: " + photoName);
        } catch (MinioException e) {
            System.err.println("Ошибка при работе с MinIO: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }


        artist.setPhoto(photoName);
        artistRepository.save(artist);
    }

    @Override
    public ArtistDto createArtist(ArtistDto dto) {
        Artist artist = convertToArtist(dto);
        Artist saved = artistRepository.save(artist);

        return convertToArtistDto(saved);
    }

    @Override
    public void updateArtist(Long id, Artist artist, MultipartFile photo) {
        Optional<Artist> existingArtist = getArtistById(id);
        if (existingArtist.isPresent()) {
            existingArtist.get().setName(artist.getName());
            existingArtist.get().setBio(artist.getBio());

            if (photo != null && !photo.isEmpty()) {
                // Генерируем уникальное имя файла
                String photoName = UUID.randomUUID() + "-" + Objects.requireNonNull(photo.getOriginalFilename());

                try {
                    // Загружаем новую фотографию в MinIO
                    minioClient.putObject(
                            PutObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(photoName)
                                    .stream(photo.getInputStream(), photo.getSize(), -1)
                                    .contentType(photo.getContentType())
                                    .build()
                    );

                    // Удаляем старую фотографию из MinIO, если она есть
                    if (existingArtist.get().getPhoto() != null) {
                        minioClient.removeObject(RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(existingArtist.get().getPhoto())
                                .build());
                    }

                    existingArtist.get().setPhoto(photoName);

                } catch (MinioException e) {
                    System.err.println("Ошибка при работе с MinIO: " + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.err.println("Ошибка при чтении файла: " + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    System.err.println("Неожиданная ошибка: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            artistRepository.save(existingArtist.get());
        }

    }

    @Override
    public ArtistDto updateArtist(Long id, ArtistDto dto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        artist.setName(dto.getName());
        artist.setBio(dto.getBio());
        artist.setSongs(dto.getSongs());
        artist.setPhoto(dto.getPhoto());
        artist.setAlbums(dto.getAlbums());

        Artist updated = artistRepository.save(artist);

        return convertToArtistDto(updated);

    }

    @Override
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    @Override
    public Artist convertToArtist(ArtistDto artistDto) {
        return ArtistMapper.INSTANCE.convertToArtist(artistDto);
    }

    @Override
    public ArtistDto convertToArtistDto(Artist artist) {
        return ArtistMapper.INSTANCE.convertToArtistDto(artist);
    }


}
