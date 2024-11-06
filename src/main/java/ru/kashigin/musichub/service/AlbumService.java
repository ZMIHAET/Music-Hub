package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.AlbumDto;
import ru.kashigin.musichub.model.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {
    List<Album> getAllAlbums();
    Optional<Album> getAlbumById(Long id);
    Album createAlbum(Album album);
    Album updateAlbum(Long id, Album album);
    void deleteAlbum(Long id);
    void addArtist(AlbumDto albumDto, Long artistId);
    Album convertToAlbum(AlbumDto aLbumDto);
}
