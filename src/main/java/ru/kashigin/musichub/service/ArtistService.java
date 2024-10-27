package ru.kashigin.musichub.service;

import org.springframework.web.multipart.MultipartFile;
import ru.kashigin.musichub.dto.ArtistDto;
import ru.kashigin.musichub.model.Artist;

import java.util.List;

public interface ArtistService {
    List<Artist> getAllArtists();
    Artist getArtistById(Long id);
    Artist createArtist(Artist artist, MultipartFile photo);
    Artist updateArtist(Long id, Artist artist, MultipartFile photo);
    void deleteArtist(Long id);
    Artist convertToArtist(ArtistDto artistDto);
}
