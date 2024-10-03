package ru.kashigin.musichub.service.impls;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.repository.AlbumRepository;
import ru.kashigin.musichub.repository.ArtistRepository;
import ru.kashigin.musichub.service.AlbumService;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public Album getAlbumById(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    @Override
    public Album createAlbum(Album album) {
        if (album.getArtist() == null || album.getArtist().getArtistId() == null)
            album.setArtist(null);

        return albumRepository.save(album);
    }

    @Override
    public Album updateAlbum(Long id, Album album) {
        Album existingAlbum = getAlbumById(id);
        if (existingAlbum != null){
            existingAlbum.setName(album.getName());
            existingAlbum.setRelease(album.getRelease());

            if (album.getArtist() == null || album.getArtist().getArtistId() == null)
                album.setArtist(null);

            return albumRepository.save(existingAlbum);
        }
        return null;
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addArtist(Album album, Long artistId) {
        if (artistId != null){
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
            album.setArtist(artist);

            artist.getAlbums().add(album);
            artistRepository.save(artist);
        }
    }
}
