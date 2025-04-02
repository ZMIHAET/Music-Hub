package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.PlaylistDto;
import ru.kashigin.musichub.model.Playlist;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    List<Playlist> getAllPlaylists();
    List<PlaylistDto> getAllPlaylistsApi();

    Optional<Playlist> getPlaylistById(Long id);
    Optional<PlaylistDto> getPlaylistByIdApi(Long id);

    void createPlaylist(Playlist playlist);
    PlaylistDto createPlaylist(PlaylistDto playlistDto);

    void updatePlaylist(Long id, Playlist playlist);
    PlaylistDto updatePlaylist(Long id, PlaylistDto playlistDto);

    void deletePlaylist(Long id);
    void addOwner(PlaylistDto playlistDto, Long personId);
    Playlist convertToPlaylist(PlaylistDto playlistDto);
    PlaylistDto convertToPlaylistDto(Playlist playlist);
}
