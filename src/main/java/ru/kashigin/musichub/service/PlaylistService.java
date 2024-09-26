package ru.kashigin.musichub.service;

import ru.kashigin.musichub.model.Playlist;

import java.util.List;

public interface PlaylistService {
    List<Playlist> getAllPlaylists();
    Playlist getPlaylistById(Long id);
    Playlist createPlaylist(Playlist playlist);
    Playlist updatePlaylist(Long id, Playlist playlist);
    void deletePlaylist(Long id);
}
