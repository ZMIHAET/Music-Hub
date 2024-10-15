package ru.kashigin.musichub.service.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.dto.PlaylistDto;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.model.Playlist;
import ru.kashigin.musichub.repository.PersonRepository;
import ru.kashigin.musichub.repository.PlaylistRepository;
import ru.kashigin.musichub.service.PlaylistService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

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
        if (playlist.getOwner() == null || playlist.getOwner().getPersonId() == null)
            playlist.setOwner(null);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist updatePlaylist(Long id, Playlist playlist) {
        Playlist existingPlaylist = getPlaylistById(id);
        if (existingPlaylist != null){
            existingPlaylist.setName(playlist.getName());
            existingPlaylist.setDescription(playlist.getDescription());

            if (playlist.getOwner() == null || playlist.getOwner().getPersonId() == null)
                existingPlaylist.setOwner(null);

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
        if (personId != null) {
            Person owner = personRepository.findById(personId)
                    .orElseThrow(() -> new IllegalArgumentException("Person not found"));

            playlist.setOwner(owner);

            owner.getPlaylists().add(playlist);
            personRepository.save(owner);
        }
    }

    @Override
    public Playlist convertToPlaylist(PlaylistDto playlistDto) {
        return modelMapper.map(playlistDto, Playlist.class);
    }
}
