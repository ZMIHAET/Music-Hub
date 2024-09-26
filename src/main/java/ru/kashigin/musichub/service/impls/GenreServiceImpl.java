package ru.kashigin.musichub.service.impls;

import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Genre;
import ru.kashigin.musichub.repository.GenreRepository;
import ru.kashigin.musichub.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre updateGenre(Long id, Genre genre) {
        if (genreRepository.findById(id).isPresent()){
            genreRepository.deleteById(id);
            return genreRepository.save(genre);
        }
        return null;
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}