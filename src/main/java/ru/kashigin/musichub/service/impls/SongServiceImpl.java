package ru.kashigin.musichub.service.impls;

import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.model.Song;
import ru.kashigin.musichub.repository.AlbumRepository;
import ru.kashigin.musichub.repository.SongRepository;
import ru.kashigin.musichub.service.SongService;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    public SongServiceImpl(SongRepository songRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
    }

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
        if (song.getAlbum() != null && song.getAlbum().getAlbumId() != null) {
            Album album = albumRepository.findById(song.getAlbum().getAlbumId())
                    .orElseThrow(() -> new IllegalArgumentException("Album not found"));
            song.setAlbum(album);
        }
        else
            song.setAlbum(null);

        return songRepository.save(song);
    }

    @Override
    public Song updateSong(Long id, Song song) {
        Song existingSong = getSongById(id);
        if (existingSong != null){
            existingSong.setName(song.getName());
            existingSong.setRelease(song.getRelease());
            existingSong.setDuration(song.getDuration());

            if (song.getAlbum() != null && song.getAlbum().getAlbumId() != null) {
                Album album = albumRepository.findById(song.getAlbum().getAlbumId())
                        .orElseThrow(() -> new IllegalArgumentException("Album not found"));
                song.setAlbum(album);
            }
            else
                song.setAlbum(null);
            songRepository.save(existingSong);
        }
        return null;
    }

    @Override
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    public void addAlbum(Song song, Long albumId) {
        if (albumId != null) {
            Album album = albumRepository.findById(albumId)
                    .orElseThrow(() -> new IllegalArgumentException("Album not found"));
            song.setAlbum(album);

            album.getSongs().add(song);
            albumRepository.save(album);
        }
    }
}
