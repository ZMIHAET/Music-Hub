package ru.kashigin.musichub.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.dto.AlbumDto;
import ru.kashigin.musichub.service.AlbumService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/albums")
@RestController
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping()
    public List<AlbumDto> getAll(){
        return albumService.getAllAlbumsApi();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDto> getById(@PathVariable Long id){
        return albumService.getAlbumByIdApi(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AlbumDto create(@RequestBody @Valid AlbumDto dto){
        return albumService.createAlbum(dto);
    }

    @PutMapping("/{id}")
    public AlbumDto update(@PathVariable Long id, @RequestBody @Valid AlbumDto albumDto){
        return albumService.updateAlbum(id, albumDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        albumService.deleteAlbum(id);
    }
}
