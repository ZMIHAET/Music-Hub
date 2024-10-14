package ru.kashigin.musichub.service.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.model.Genre;
import ru.kashigin.musichub.model.Song;
import ru.kashigin.musichub.repository.AlbumRepository;
import ru.kashigin.musichub.repository.ArtistRepository;
import ru.kashigin.musichub.repository.GenreRepository;
import ru.kashigin.musichub.repository.SongRepository;
import ru.kashigin.musichub.service.SongService;

import java.util.List;

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
    public Song getSongById(Long id) {
        return songRepository.findById(id).orElse(null);
    }

    @Override
    public Song createSong(Song song) {
        if (song.getAlbum() == null || song.getAlbum().getAlbumId() == null) {
            song.setAlbum(null);
        }
        if (song.getArtist() == null || song.getArtist().getArtistId() == null){
            song.setArtist(null);
        }

        return songRepository.save(song);
    }

    @Override
    public Song updateSong(Long id, Song song) {
        Song existingSong = getSongById(id);
        if (existingSong != null) {
            existingSong.setName(song.getName());
            existingSong.setRelease(song.getRelease());
            existingSong.setDuration(song.getDuration());
            existingSong.setGenre(song.getGenre());
            if (song.getAlbum() == null || song.getAlbum().getAlbumId() == null) {
                song.setAlbum(null);
            }
            if (song.getArtist() == null || song.getArtist().getArtistId() == null) {
                song.setArtist(null);
            }
            songRepository.save(existingSong);
        }
        return null;
    }

    @Override
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addAlbum(Song song, Long albumId) {
        if (albumId != null) {
            Album album = albumRepository.findById(albumId)
                    .orElseThrow(() -> new IllegalArgumentException("Album not found"));
            song.setAlbum(album);

            album.getSongs().add(song);
            albumRepository.save(album);
        }
    }

    @Override
    @Transactional
    public void addArtist(Song song, Long artistId) {
        if (artistId != null) {
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
            song.setArtist(artist);

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
}
