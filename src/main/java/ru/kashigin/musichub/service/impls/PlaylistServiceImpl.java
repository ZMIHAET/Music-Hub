package ru.kashigin.musichub.service.impls;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.model.Playlist;
import ru.kashigin.musichub.repository.PersonRepository;
import ru.kashigin.musichub.repository.PlaylistRepository;
import ru.kashigin.musichub.service.PlaylistService;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PersonRepository personRepository;


    public PlaylistServiceImpl(PlaylistRepository playlistRepository, PersonRepository personRepository) {
        this.playlistRepository = playlistRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id).orElse(null);
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist updatePlaylist(Long id, Playlist playlist) {
        Playlist existingPlaylist = getPlaylistById(id);
        if (existingPlaylist != null){
            existingPlaylist.setName(playlist.getName());
            existingPlaylist.setDescription(playlist.getDescription());

            playlistRepository.save(existingPlaylist);
        }
        return null;
    }

    @Override
    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addOwner(Playlist playlist, Long personId) {
        Person owner = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));

        playlist.setOwner(owner);

        owner.getPlaylists().add(playlist);
        personRepository.save(owner);
    }
}
