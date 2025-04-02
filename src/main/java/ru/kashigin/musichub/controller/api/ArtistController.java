package ru.kashigin.musichub.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.dto.ArtistDto;
import ru.kashigin.musichub.service.ArtistService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/artists")
@RestController
public class ArtistController {
    private final ArtistService artistService;
    @GetMapping
    public List<ArtistDto> getAll(){
        return artistService.getAllArtistsApi();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getById(@PathVariable Long id) {
        return artistService.getArtistByIdApi(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ArtistDto create(@RequestBody @Valid ArtistDto dto){
        return artistService.createArtist(dto);
    }

    @PutMapping("/{id}")
    public ArtistDto update(@PathVariable Long id, @RequestBody @Valid ArtistDto dto){
        return artistService.updateArtist(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        artistService.deleteArtist(id);
    }
}
