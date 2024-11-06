package ru.kashigin.musichub.controller.views;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.dto.PlaylistDto;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.model.Playlist;
import ru.kashigin.musichub.service.PersonService;
import ru.kashigin.musichub.service.PlaylistService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ViewControllerPlaylist {
    private final PlaylistService playlistService;

    @GetMapping("/playlists")
    public String viewPlaylists(Model model){
        model.addAttribute("playlists", playlistService.getAllPlaylists());
        return "pla/playlists";
    }

    @GetMapping("/playlists/new")
    public String addPlaylist(Model model, @RequestParam(required = false) Long personId){
        PlaylistDto playlistDto = new PlaylistDto();
        if (personId != null)
            playlistService.addOwner(playlistDto, personId);
        model.addAttribute("playlist", playlistDto);
        return "pla/addPlaylist";
    }

    @PostMapping("/playlists/new")
    public String addPlaylistSubmit(@ModelAttribute("playlist") @Valid PlaylistDto playlistDto, BindingResult bindingResult){
        Playlist playlist = playlistService.convertToPlaylist(playlistDto);
        if(bindingResult.hasErrors())
            return "pla/addPlaylist";
        playlistService.createPlaylist(playlist);
        return "redirect:/playlists";
    }

    @GetMapping("/playlists/{id}")
    public String viewPlaylist(@PathVariable("id") Long id, Model model){
        Optional<Playlist> playlist = playlistService.getPlaylistById(id);
        if (playlist.isEmpty())
            throw new RuntimeException("Playlist not found");
        model.addAttribute("playlist", playlist.get());
        return "pla/playlistDetails";
    }

    @GetMapping("/playlists/{id}/edit")
    public String editPlaylist(@PathVariable("id") Long id, Model model){
        Optional<Playlist> playlist = playlistService.getPlaylistById(id);
        if (playlist.isEmpty())
            throw new RuntimeException("Playlist not found");
        model.addAttribute("playlist", playlist.get());
        return "pla/editPlaylist";
    }

    @PostMapping("/playlists/{id}/edit")
    public String editPlaylistSubmit(@PathVariable("id") Long id, @ModelAttribute @Valid Playlist playlist,
                                  BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "pla/editPlaylist";
        if (playlistService.getPlaylistById(id).isEmpty())
            throw new RuntimeException("Playlist not found");

        playlistService.updatePlaylist(id, playlist);
        return "redirect:/playlists/" + id;
    }

    @PostMapping("/playlists/{id}")
    public String deletePlaylist(@PathVariable("id") Long id, @RequestParam("_method") String method){
        if ("delete".equalsIgnoreCase(method))
            playlistService.deletePlaylist(id);
        return "redirect:/playlists";
    }
}
