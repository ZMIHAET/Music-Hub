package ru.kashigin.musichub.service.impls;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kashigin.musichub.dto.ArtistDto;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.repository.ArtistRepository;
import ru.kashigin.musichub.service.ArtistService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import java.util.UUID;
import java.io.InputStream;



@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final ModelMapper modelMapper;
    private final MinioClient minioClient;

    private final String bucketName = "artist-photos";

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    @Override
    public Artist createArtist(Artist artist, MultipartFile photo) {
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
            System.out.println("Файл успешно загружен в MinIO с именем: " + photoName);
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
        return artistRepository.save(artist);
    }

    @Override
    public Artist updateArtist(Long id, Artist artist, MultipartFile photo) {
        Artist existingArtist = getArtistById(id);
        if (existingArtist != null) {
            existingArtist.setName(artist.getName());
            existingArtist.setBio(artist.getBio());

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
                    if (existingArtist.getPhoto() != null) {
                        minioClient.removeObject(RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(existingArtist.getPhoto())
                                .build());
                    }

                    existingArtist.setPhoto(photoName);

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

            return artistRepository.save(existingArtist);
        }

        return null;
    }

    @Override
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    @Override
    public Artist convertToArtist(ArtistDto artistDto) {
        return modelMapper.map(artistDto, Artist.class);
    }
}
