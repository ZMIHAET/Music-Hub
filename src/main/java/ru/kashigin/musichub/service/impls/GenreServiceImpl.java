package ru.kashigin.musichub.service.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.dto.GenreDto;
import ru.kashigin.musichub.model.Genre;
import ru.kashigin.musichub.repository.GenreRepository;
import ru.kashigin.musichub.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

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
        Genre existingGenre = getGenreById(id);
        if (existingGenre != null){
            existingGenre.setName(genre.getName());
            existingGenre.setDescription(genre.getDescription());

            return genreRepository.save(existingGenre);
        }
        return null;
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Genre convertToGenre(GenreDto genreDto) {
        return modelMapper.map(genreDto, Genre.class);
    }
}
