package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.AlbumDto;
import ru.kashigin.musichub.model.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {
    List<Album> getAllAlbums();
    List<AlbumDto> getAllAlbumsApi();

    Optional<Album> getAlbumById(Long id);
    Optional<AlbumDto> getAlbumByIdApi(Long id);

    void createAlbum(Album album);
    AlbumDto createAlbum(AlbumDto albumDto);

    void updateAlbum(Long id, Album album);
    AlbumDto updateAlbum(Long id, AlbumDto albumDto);

    void deleteAlbum(Long id);
    void addArtist(AlbumDto albumDto, Long artistId);
    Album convertToAlbum(AlbumDto albumDto);
    AlbumDto convertToAlbumDto(Album album);
}
