package ru.kashigin.musichub.controller.views;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kashigin.musichub.dto.ArtistDto;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.service.ArtistService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.minio.MinioClient;
import io.minio.GetObjectArgs;
import java.io.InputStream;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ViewControllerArtist {
    private final ArtistService artistService;
    private final MinioClient minioClient;


    @GetMapping("/artists")
    public String viewArtists(Model model){
        model.addAttribute("artists", artistService.getAllArtists());
        return "art/artists";
    }

    @GetMapping("/artists/new")
    public String addArtist(Model model){
        model.addAttribute("artist", new ArtistDto());
        return "art/addArtist";
    }

    @PostMapping("/artists/new")
    public String addArtistSubmit(@ModelAttribute("artist") @Valid ArtistDto artistDto, BindingResult bindingResult,
                                  @RequestParam("photo") MultipartFile photo) {
        Artist artist = artistService.convertToArtist(artistDto);
/*        if (bindingResult.hasErrors()) {
            return "art/addArtist";
        }*/

        artistService.createArtist(artist, photo);
        return "redirect:/artists";
    }

    @GetMapping("/artists/{id}")
    public String viewArtist(@PathVariable("id") Long id, Model model){
        Optional<Artist> artist = artistService.getArtistById(id);
        if (artist.isEmpty())
            throw new RuntimeException("Artist not found");

        model.addAttribute("artist", artist.get());
        model.addAttribute("photoUrl", "/artists/photo/" + artist.get().getPhoto());
        return "art/artistDetails";
    }
    @GetMapping("/artists/photo/{photo}")
    public ResponseEntity<Resource> getArtistPhoto(@PathVariable String photo) {
        try {
            String bucketName = "artist-photos";
            InputStream photoStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(photo)
                            .build()
            );

            Resource photoResource = new InputStreamResource(photoStream);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(photoResource);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке фото", e);
        }
    }

    @GetMapping("/artists/{id}/edit")
    public String editArtist(@PathVariable("id") Long id, Model model){
        Optional<Artist> artist = artistService.getArtistById(id);
        if (artist.isEmpty())
            throw new RuntimeException("Artist not found");
        model.addAttribute("artist", artist.get());
        return "art/editArtist";
    }

    @PostMapping("/artists/{id}/edit")
    public String editArtistSubmit(@PathVariable("id") Long id, @ModelAttribute @Valid Artist artist,
                                   BindingResult bindingResult, @RequestParam("photo") MultipartFile photo){
/*
        if (bindingResult.hasErrors())
            return "art/editArtist";
*/
        if (artistService.getArtistById(id).isEmpty())
            throw new RuntimeException("Artist not found");
        artistService.updateArtist(id, artist, photo);
        return "redirect:/artists/" + id;
    }

    @PostMapping("/artists/{id}")
    public String deleteArtist(@PathVariable("id") Long id, @RequestParam("_method") String method){
        if ("delete".equalsIgnoreCase(method))
            artistService.deleteArtist(id);
        return "redirect:/artists";
    }
}
