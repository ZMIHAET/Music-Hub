package ru.kashigin.musichub.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.dto.GenreDto;
import ru.kashigin.musichub.model.Genre;
import ru.kashigin.musichub.repository.GenreRepository;
import ru.kashigin.musichub.service.GenreService;
import ru.kashigin.musichub.service.mappers.GenreMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre updateGenre(Long id, Genre genre) {
        Optional<Genre> existingGenre = getGenreById(id);
        if (existingGenre.isPresent()){
            existingGenre.get().setName(genre.getName());
            existingGenre.get().setDescription(genre.getDescription());

            return genreRepository.save(existingGenre.get());
        }
        return null;
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Genre convertToGenre(GenreDto genreDto) {
        return GenreMapper.INSTANCE.convertToGenre(genreDto);
    }
}
