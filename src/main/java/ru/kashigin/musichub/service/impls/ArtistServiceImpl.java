package ru.kashigin.musichub.service.impls;

import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.repository.ArtistRepository;
import ru.kashigin.musichub.service.ArtistService;

import java.util.List;

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
        if (artistRepository.findById(id).isPresent()){
            artistRepository.deleteById(id);
            return artistRepository.save(artist);
        }
        return null;
    }

    @Override
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }
}
