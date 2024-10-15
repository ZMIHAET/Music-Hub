package ru.kashigin.musichub.service.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kashigin.musichub.dto.ArtistDto;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.repository.ArtistRepository;
import ru.kashigin.musichub.service.ArtistService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    @Override
    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public Artist updateArtist(Long id, Artist artist) {
        Artist existingArtist = getArtistById(id);
        if (existingArtist != null) {
            existingArtist.setName(artist.getName());
            existingArtist.setBio(artist.getBio());

            //для фотки

            artistRepository.save(existingArtist);
        }

        return null;
    }

    @Override
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    @Override
    public Artist convertToArtist(ArtistDto artistDto) {
        return modelMapper.map(artistDto, Artist.class);
    }
}
