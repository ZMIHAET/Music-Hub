package ru.kashigin.musichub.service;

import org.springframework.web.multipart.MultipartFile;
import ru.kashigin.musichub.dto.ArtistDto;
import ru.kashigin.musichub.model.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistService {
    List<Artist> getAllArtists();
    List<ArtistDto> getAllArtistsApi();

    Optional<Artist> getArtistById(Long id);
    Optional<ArtistDto> getArtistByIdApi(Long id);

    void createArtist(Artist artist, MultipartFile photo);
    ArtistDto createArtist(ArtistDto dto);

    void updateArtist(Long id, Artist artist, MultipartFile photo);
    ArtistDto updateArtist(Long id, ArtistDto dto);

    void deleteArtist(Long id);
    Artist convertToArtist(ArtistDto artistDto);
    ArtistDto convertToArtistDto(Artist artist);
}
