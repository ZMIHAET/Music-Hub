package ru.kashigin.musichub.service.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.dto.SongDto;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.model.Genre;
import ru.kashigin.musichub.model.Song;
import ru.kashigin.musichub.repository.AlbumRepository;
import ru.kashigin.musichub.repository.ArtistRepository;
import ru.kashigin.musichub.repository.GenreRepository;
import ru.kashigin.musichub.repository.SongRepository;
import ru.kashigin.musichub.service.SongService;
import ru.kashigin.musichub.service.mappers.SongMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public List<SongDto> getAllSongsApi() {
        return songRepository.findAll().stream()
                .map(this::convertToSongDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public Optional<SongDto> getSongByIdApi(Long id) {
        return songRepository.findById(id)
                .map((this::convertToSongDto)); 
    }
    @Override
    public void createSong(Song song) {
        if (song.getAlbum() == null || song.getAlbum().getAlbumId() == null) {
            song.setAlbum(null);
        }
        if (song.getArtist() == null || song.getArtist().getArtistId() == null){
            song.setArtist(null);
        }

        songRepository.save(song);
    }

    @Override
    public SongDto createSong(SongDto songDto) {
        Song song = convertToSong(songDto);

        Song saved = songRepository.save(song);
        return convertToSongDto(saved);
    }

    @Override
    public Song updateSong(Long id, Song song) {
        Optional<Song> existingSong = getSongById(id);
        if (existingSong.isPresent()) {
            existingSong.get().setName(song.getName());
            existingSong.get().setRelease(song.getRelease());
            existingSong.get().setDuration(song.getDuration());
            existingSong.get().setGenre(song.getGenre());
            if (song.getAlbum() == null || song.getAlbum().getAlbumId() == null) {
                song.setAlbum(null);
            }
            if (song.getArtist() == null || song.getArtist().getArtistId() == null) {
                song.setArtist(null);
            }
            songRepository.save(existingSong.get());
        }
        return null;
    }

    @Override
    public SongDto updateSong(Long id, SongDto songDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found"));

        song.setName(songDto.getName());
        song.setDuration(songDto.getDuration());
        song.setRelease(songDto.getRelease());
        song.setArtist(songDto.getArtist());
        song.setAlbum(songDto.getAlbum());
        song.setGenre(songDto.getGenre());

        Song updated = songRepository.save(song);
        return convertToSongDto(updated);
    }

    @Override
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addAlbum(SongDto songDto, Long albumId) {
        if (albumId != null) {
            Album album = albumRepository.findById(albumId)
                    .orElseThrow(() -> new IllegalArgumentException("Album not found"));
            Song song = convertToSong(songDto);
            song.setAlbum(album);

            songDto.setAlbum(album);

            album.getSongs().add(song);
            albumRepository.save(album);
        }
    }

    @Override
    @Transactional
    public void addArtist(SongDto songDto, Long artistId) {
        if (artistId != null) {
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
            Song song = convertToSong(songDto);
            song.setArtist(artist);

            songDto.setArtist(artist);

            artist.getSongs().add(song);
            artistRepository.save(artist);
        }
    }

    @Override
    @Transactional
    public void setGenre(Song song, Long genreId) {
        if (genreId != null){
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Genre not found"));
            song.setGenre(genre);

            if (!genre.getSongs().contains(song)) {
                genre.getSongs().add(song);
                genreRepository.save(genre);
            }
        }
    }

    @Override
    public Song convertToSong(SongDto songDto) {
        return SongMapper.INSTANCE.convertToSong(songDto);
    }

    @Override
    public SongDto convertToSongDto(Song song){
        return SongMapper.INSTANCE.convertToSongDto(song);
    }
}