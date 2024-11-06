package ru.kashigin.musichub.service.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.dto.AlbumDto;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.repository.AlbumRepository;
import ru.kashigin.musichub.repository.ArtistRepository;
import ru.kashigin.musichub.service.AlbumService;
import ru.kashigin.musichub.service.mappers.AlbumMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @Override
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    public Album createAlbum(Album album) {
        if (album.getArtist() == null || album.getArtist().getArtistId() == null)
            album.setArtist(null);

        return albumRepository.save(album);
    }

    @Override
    public Album updateAlbum(Long id, Album album) {
        Optional<Album> existingAlbum = getAlbumById(id);
        if (existingAlbum.isPresent()){
            existingAlbum.get().setName(album.getName());
            existingAlbum.get().setRelease(album.getRelease());

            if (album.getArtist() == null || album.getArtist().getArtistId() == null)
                album.setArtist(null);

            return albumRepository.save(existingAlbum.get());
        }
        return null;
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addArtist(AlbumDto albumDto, Long artistId) {
        if (artistId != null){
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new IllegalArgumentException("Artist not found"));

            Album album = convertToAlbum(albumDto);

            album.setArtist(artist);

            albumDto.setArtist(artist);

            artist.getAlbums().add(album);
            artistRepository.save(artist);
        }
    }

    @Override
    public Album convertToAlbum(AlbumDto aLbumDto) {
        return AlbumMapper.INSTANCE.convertToAlbum(aLbumDto);
    }
}
