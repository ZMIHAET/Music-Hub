package ru.kashigin.musichub.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.dto.PlaylistDto;
import ru.kashigin.musichub.service.PlaylistService;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @GetMapping
    public List<PlaylistDto> getAll() {
        return playlistService.getAllPlaylistsApi();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDto> getById(@PathVariable Long id) {
        return playlistService.getPlaylistByIdApi(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public PlaylistDto create(@RequestBody @Valid PlaylistDto dto) {
        return playlistService.createPlaylist(dto);
    }

    @PutMapping("/{id}")
    public PlaylistDto update(@PathVariable Long id, @RequestBody @Valid PlaylistDto dto) {
        return playlistService.updatePlaylist(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
    }
}
