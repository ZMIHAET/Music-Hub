package ru.kashigin.musichub.controller.views;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.dto.AlbumDto;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.service.AlbumService;

@Controller
@RequiredArgsConstructor
public class ViewControllerAlbum {
    private final AlbumService albumService;

    @GetMapping("/albums")
    public String viewAlbums(Model model){
        model.addAttribute("albums", albumService.getAllAlbums());
        return "alb/albums";
    }

    @GetMapping("/albums/new")
    public String addAlbum(Model model, @RequestParam(required = false) Long artistId){
        Album album = new Album();
        if (artistId != null)
            albumService.addArtist(album, artistId);
        model.addAttribute("album", album);
        return "alb/addAlbum";
    }

    @PostMapping("/albums/new")
    public String addAlbum(@ModelAttribute @Valid AlbumDto albumDto, BindingResult bindingResult){
        Album album = albumService.convertToAlbum(albumDto);
        if (bindingResult.hasErrors())
            return "alb/addAlbum";
        albumService.createAlbum(album);
        return "redirect:/albums";
    }

    @GetMapping("/albums/{id}")
    public String viewAlbum(@PathVariable("id") Long id, Model model){
        Album album = albumService.getAlbumById(id);
        if (album == null)
            throw new RuntimeException("Album not found");
        model.addAttribute("album", album);
        return "alb/albumDetails";
    }

    @GetMapping("/albums/{id}/edit")
    public String editAlbum(@PathVariable("id") Long id, Model model){
        Album album = albumService.getAlbumById(id);
        if (album == null)
            throw new RuntimeException("Album not found");
        model.addAttribute("album", album);
        return "alb/editAlbum";
    }

    @PostMapping("/albums/{id}/edit")
    public String editAlbumSubmit(@PathVariable("id") Long id, @ModelAttribute @Valid AlbumDto albumDto,
                                  BindingResult bindingResult){
        Album album = albumService.convertToAlbum(albumDto);
        if (bindingResult.hasErrors())
            return "alb/editAlbum";
        if (albumService.getAlbumById(id) == null)
            throw new RuntimeException("Album not found");
        albumService.updateAlbum(id, album);
        return "redirect:/albums/" + id;
    }

    @PostMapping("/albums/{id}")
    public String deleteAlbum(@PathVariable("id") Long id, @RequestParam("_method") String method){
        if ("delete".equalsIgnoreCase(method))
            albumService.deleteAlbum(id);
        return "redirect:/albums";
    }
}
