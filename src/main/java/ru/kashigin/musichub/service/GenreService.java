package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.GenreDto;
import ru.kashigin.musichub.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getAllGenres();
    Optional<Genre> getGenreById(Long id);
    Genre createGenre(Genre genre);
    Genre updateGenre(Long id, Genre genre);
    void deleteGenre(Long id);
    Genre convertToGenre(GenreDto genreDto);
}
