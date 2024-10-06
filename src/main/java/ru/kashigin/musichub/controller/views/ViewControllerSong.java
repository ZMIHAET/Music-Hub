package ru.kashigin.musichub.controller.views;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.model.Song;
import ru.kashigin.musichub.service.ArtistService;
import ru.kashigin.musichub.service.SongService;

@Controller
public class ViewControllerSong {
    private final SongService songService;

    public ViewControllerSong(SongService songService, ArtistService artistService) {
        this.songService = songService;
    }
    @GetMapping("/songs")
    public String viewSongs(Model model){
        model.addAttribute("songs", songService.getAllSongs());
        return "/son/songs";
    }
    @GetMapping("/songs/new")
    public String addSong(Model model, @RequestParam(required = false) Long albumId,
                          @RequestParam(required = false) Long artistId){
        Song song = new Song();
        if (albumId != null)
            songService.addAlbum(song, albumId);
        if (artistId != null)
            songService.addArtist(song, artistId);
        model.addAttribute("song", song);
        return "/son/addSong";
    }
    @PostMapping("/songs/new")
    public String addSongSubmit(@ModelAttribute @Valid Song song, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/son/addSong";
        songService.createSong(song);
        return "redirect:/songs";
    }
    @GetMapping("/songs/{id}")
    public String viewSong(@PathVariable("id") Long id, Model model){
        Song song = songService.getSongById(id);
        Artist artist = null;
        if (song == null)
            throw new RuntimeException("Song not found");
        if (song.getArtist() != null && song.getArtist().getArtistId() != null) {
            artist = song.getArtist();
        }
        model.addAttribute("song", song);
        model.addAttribute("artist", artist);
        return "/son/songDetails";
    }
    @GetMapping("/songs/{id}/edit")
    public String editSong(@PathVariable("id") Long id, Model model){
        Song song = songService.getSongById(id);
        if (song == null)
            throw new RuntimeException("Song not found");
        model.addAttribute("song", song);
        return "/son/editSong";
    }
    @PostMapping("/songs/{id}/edit")
    public String editSongSubmit(@PathVariable("id") Long id, @ModelAttribute @Valid Song song,
                                 BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/son/editSong";
        if (songService.getSongById(id) == null)
            throw new RuntimeException("Song not found");
        songService.updateSong(id, song);
        return "redirect:/songs/" + id;
    }
    @PostMapping("/songs/{id}")
    public String deleteSong(@PathVariable("id") Long id, @RequestParam("_method") String method){
        if ("delete".equalsIgnoreCase(method))
            songService.deleteSong(id);
        return "redirect:/songs";
    }
}
