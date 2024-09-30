package ru.kashigin.musichub.service.impls;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

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
        Optional<Artist> existingArtistOptional = artistRepository.findById(id);
        if (existingArtistOptional.isPresent()) {
            Artist existingArtist = existingArtistOptional.get();
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
}
