package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.PlaylistDto;
import ru.kashigin.musichub.model.Playlist;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    List<Playlist> getAllPlaylists();
    Optional<Playlist> getPlaylistById(Long id);
    Playlist createPlaylist(Playlist playlist);
    Playlist updatePlaylist(Long id, Playlist playlist);
    void deletePlaylist(Long id);
    void addOwner(PlaylistDto playlistDto, Long personId);
    Playlist convertToPlaylist(PlaylistDto playlistDto);
}
