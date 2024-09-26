package ru.kashigin.musichub.service.impls;

import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Song;
import ru.kashigin.musichub.repository.SongRepository;
import ru.kashigin.musichub.service.SongService;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
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
        return songRepository.save(song);
    }

    @Override
    public Song updateSong(Long id, Song song) {
        if (songRepository.findById(id).isPresent()){
            songRepository.deleteById(id);
            return songRepository.save(song);
        }
        return null;
    }

    @Override
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}
