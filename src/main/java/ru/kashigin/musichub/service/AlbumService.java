package ru.kashigin.musichub.service;

import ru.kashigin.musichub.model.Album;

import java.util.List;

public interface AlbumService {
    List<Album> getAllAlbums();
    Album getAlbumById(Long id);
    Album createAlbum(Album album);
    Album updateAlbum(Long id, Album album);
    void deleteAlbum(Long id);
    void addArtist(Album album, Long artistId);
}
