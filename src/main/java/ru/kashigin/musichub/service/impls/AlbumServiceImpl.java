package ru.kashigin.musichub.service.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

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
    public List<AlbumDto> getAllAlbumsApi() {
        return albumRepository.findAll().stream()
                .map(this::convertToAlbumDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    public Optional<AlbumDto> getAlbumByIdApi(Long id) {
        return albumRepository.findById(id)
                .map(this::convertToAlbumDto);
    }

    @Override
    public void createAlbum(Album album) {
        if (album.getArtist() == null || album.getArtist().getArtistId() == null)
            album.setArtist(null);

        albumRepository.save(album);
    }

    @Override
    public AlbumDto createAlbum(AlbumDto albumDto) {
        Album album = convertToAlbum(albumDto);

        Album saved = albumRepository.save(album);
        return convertToAlbumDto(saved);
    }

    @Override
    public void updateAlbum(Long id, Album album) {
        Optional<Album> existingAlbum = getAlbumById(id);
        if (existingAlbum.isPresent()){
            existingAlbum.get().setName(album.getName());
            existingAlbum.get().setRelease(album.getRelease());

            if (album.getArtist() == null || album.getArtist().getArtistId() == null)
                album.setArtist(null);

            albumRepository.save(existingAlbum.get());
        }
    }

    @Override
    public AlbumDto updateAlbum(Long id, AlbumDto albumDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album not found"));

        album.setName(albumDto.getName());
        album.setRelease(albumDto.getRelease());
        album.setArtist(albumDto.getArtist());

        Album updated = albumRepository.save(album);
        return convertToAlbumDto(updated);
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
    public Album convertToAlbum(AlbumDto albumDto) {
        return AlbumMapper.INSTANCE.convertToAlbum(albumDto);
    }

    @Override
    public AlbumDto convertToAlbumDto(Album album) {
        return AlbumMapper.INSTANCE.convertToAlbumDto(album);
    }
}
