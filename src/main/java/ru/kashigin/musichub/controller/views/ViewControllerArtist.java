package ru.kashigin.musichub.controller.views;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.service.ArtistService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
public class ViewControllerArtist {
    private final ArtistService artistService;

    public ViewControllerArtist(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artists")
    public String viewArtists(Model model){
        model.addAttribute("artists", artistService.getAllArtists());
        return "art/artists";
    }

    @GetMapping("/artists/new")
    public String addArtist(Model model){
        model.addAttribute("artist", new Artist());
        return "art/addArtist";
    }

    @PostMapping("/artists/new")
    public String addArtistSubmit(@ModelAttribute @Valid Artist artist, BindingResult bindingResult,
                                  @RequestParam("photo") MultipartFile file, Model model) {
        /*
        if (bindingResult.hasErrors()) {
            return "art/addArtist";
        }
         */

        if (file.isEmpty()) {
            model.addAttribute("error", "file_not_uploaded");
            model.addAttribute("artist", artist);
            return "art/addArtist";
        }

        try {
            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectory(uploadDir);
            }

            byte[] bytes = file.getBytes();
            Path path = uploadDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.write(path, bytes);

            artist.setPhoto(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "file_upload_failed");
            model.addAttribute("artist", artist);
            return "art/addArtist";
        }

        artistService.createArtist(artist);
        return "redirect:/artists";
    }

    @GetMapping("/artists/{id}")
    public String viewArtist(@PathVariable("id") Long id, Model model){
        Artist artist = artistService.getArtistById(id);
        if (artist == null)
            throw new RuntimeException("Artist not found");

        model.addAttribute("artist", artist);
        return "art/artistDetails";
    }

    @GetMapping("/artists/{id}/edit")
    public String editArtist(@PathVariable("id") Long id, Model model){
        Artist artist = artistService.getArtistById(id);
        if (artist == null)
            throw new RuntimeException("Artist not found");
        model.addAttribute("artist", artist);
        return "art/editArtist";
    }

    @PostMapping("/artists/{id}/edit")
    public String editArtistSubmit(@PathVariable("id") Long id, @ModelAttribute @Valid Artist artist,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "art/editArtist";
        if (artistService.getArtistById(id) == null)
            throw new RuntimeException("Artist not found");
        artistService.updateArtist(id, artist);
        return "redirect:/artists/" + id;
    }

    @PostMapping("/artists/{id}")
    public String deleteArtist(@PathVariable("id") Long id, @RequestParam("_method") String method){
        if ("delete".equalsIgnoreCase(method))
            artistService.deleteArtist(id);
        return "redirect:/artists";
    }
}
