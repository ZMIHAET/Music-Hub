package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.SongDto;
import ru.kashigin.musichub.model.Song;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> getAllSongs();
    List<SongDto> getAllSongsApi();

    Optional<Song> getSongById(Long id);
    Optional<SongDto> getSongByIdApi(Long id);

    void createSong(Song song);
    SongDto createSong(SongDto songDto);

    Song updateSong(Long id, Song song);
    SongDto updateSong(Long id, SongDto songDto);

    void deleteSong(Long id);
    void addAlbum(SongDto songDto, Long albumId);
    void addArtist(SongDto songDto, Long artistId);
    void setGenre(Song song, Long genreId);
    Song convertToSong(SongDto songDto);
    SongDto convertToSongDto(Song song);
}
