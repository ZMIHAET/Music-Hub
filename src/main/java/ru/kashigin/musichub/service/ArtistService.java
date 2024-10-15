package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.ArtistDto;
import ru.kashigin.musichub.model.Artist;

import java.util.List;

public interface ArtistService {
    List<Artist> getAllArtists();
    Artist getArtistById(Long id);
    Artist createArtist(Artist artist);
    Artist updateArtist(Long id, Artist artist);
    void deleteArtist(Long id);
    Artist convertToArtist(ArtistDto artistDto);
}
