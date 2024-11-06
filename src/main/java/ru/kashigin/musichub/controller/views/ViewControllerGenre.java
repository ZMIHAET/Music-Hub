package ru.kashigin.musichub.controller.views;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import ru.kashigin.musichub.dto.GenreDto;
import ru.kashigin.musichub.model.Genre;
import ru.kashigin.musichub.service.GenreService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ViewControllerGenre {
    private final GenreService genreService;

    @GetMapping("/genres")
    public String viewGenres(Model model){
        model.addAttribute("genres", genreService.getAllGenres());
        return "gen/genres";
    }

    @GetMapping("/genres/new")
    public String addGenre(Model model){
        model.addAttribute("genre", new GenreDto());
        return "gen/addGenre";
    }

    @PostMapping("/genres/new")
    public String addGenreSubmit(@ModelAttribute("genre") @Valid GenreDto genreDto, BindingResult bindingResult){
        Genre genre = genreService.convertToGenre(genreDto);
        if(bindingResult.hasErrors())
            return "gen/addGenre";
        genreService.createGenre(genre);
        return "redirect:/genres";
    }

    @GetMapping("/genres/{id}")
    public String viewGenre(@PathVariable("id") Long id, Model model){
        Optional<Genre> genre = genreService.getGenreById(id);
        if (genre.isEmpty())
            throw new RuntimeException("Genre not found");
        model.addAttribute("genre", genre.get());
        return "gen/genreDetails";
    }

    @GetMapping("/genres/{id}/edit")
    public String editGenre(@PathVariable("id") Long id, Model model){
        Optional<Genre> genre = genreService.getGenreById(id);
        if (genre.isEmpty())
            throw new RuntimeException("Genre not found");
        model.addAttribute("genre", genre.get());
        return "gen/editGenre";
    }

    @PostMapping("/genres/{id}/edit")
    public String editGenreSubmit(@PathVariable("id") Long id, @ModelAttribute @Valid GenreDto genreDto,
                                  BindingResult bindingResult){
        Genre genre = genreService.convertToGenre(genreDto);
        if (bindingResult.hasErrors())
            return "gen/editGenre";
        if (genreService.getGenreById(id).isEmpty())
            throw new RuntimeException("Genre not found");

        genreService.updateGenre(id, genre);
        return "redirect:/genres/" + id;
    }

    @PostMapping("/genres/{id}")
    public String deleteGenre(@PathVariable("id") Long id, @RequestParam("_method") String method){
        if ("delete".equalsIgnoreCase(method))
            genreService.deleteGenre(id);
        return "redirect:/genres";
    }
}
