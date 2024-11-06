package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.SongDto;
import ru.kashigin.musichub.model.Song;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> getAllSongs();
    Optional<Song> getSongById(Long id);
    Song createSong(Song song);
    Song updateSong(Long id, Song song);
    void deleteSong(Long id);
    void addAlbum(SongDto songDto, Long albumId);
    void addArtist(SongDto songDto, Long artistId);
    void setGenre(Song song, Long genreId);
    Song convertToSong(SongDto songDto);
}
