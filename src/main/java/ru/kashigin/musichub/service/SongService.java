package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.SongDto;
import ru.kashigin.musichub.model.Song;

import java.util.List;

public interface SongService {
    List<Song> getAllSongs();
    Song getSongById(Long id);
    Song createSong(Song song);
    Song updateSong(Long id, Song song);
    void deleteSong(Long id);
    void addAlbum(Song song, Long albumId);
    void addArtist(Song song, Long artistId);
    void setGenre(Song song, Long genreId);
    Song convertToSong(SongDto songDto);
}
