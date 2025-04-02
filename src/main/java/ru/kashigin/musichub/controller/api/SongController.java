package ru.kashigin.musichub.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.dto.SongDto;
import ru.kashigin.musichub.service.SongService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/songs")
@RestController
public class SongController {
    private final SongService songService;
    @GetMapping
    public List<SongDto> getAll(){
        return songService.getAllSongsApi();
    }
    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getByIdApi(@PathVariable Long id) {
        return songService.getSongByIdApi(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SongDto create(@RequestBody @Valid SongDto dto){
        return songService.createSong(dto);
    }

    @PutMapping("/{id}")
    public SongDto update(@PathVariable Long id, @RequestBody @Valid SongDto songDto){
        return songService.updateSong(id, songDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        songService.deleteSong(id);
    }

}
