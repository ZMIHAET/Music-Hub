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
import ru.kashigin.musichub.service.mappers.PlaylistMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PersonRepository personRepository;

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public Optional<Playlist> getPlaylistById(Long id) {
        return playlistRepository.findById(id);
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        if (playlist.getOwner() == null || playlist.getOwner().getPersonId() == null)
            playlist.setOwner(null);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist updatePlaylist(Long id, Playlist playlist) {
        Optional<Playlist> existingPlaylist = getPlaylistById(id);
        if (existingPlaylist.isPresent()){
            existingPlaylist.get().setName(playlist.getName());
            existingPlaylist.get().setDescription(playlist.getDescription());


            if (playlist.getOwner() == null || playlist.getOwner().getPersonId() == null)
                existingPlaylist.get().setOwner(null);

            playlistRepository.save(existingPlaylist.get());
        }
        return null;
    }

    @Override
    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addOwner(PlaylistDto playlistDto, Long personId) {
        if (personId != null) {
            Person owner = personRepository.findById(personId)
                    .orElseThrow(() -> new IllegalArgumentException("Person not found"));

            Playlist playlist = convertToPlaylist(playlistDto);

            playlist.setOwner(owner);

            playlistDto.setOwner(owner);

            owner.getPlaylists().add(playlist);
            personRepository.save(owner);
        }
    }

    @Override
    public Playlist convertToPlaylist(PlaylistDto playlistDto) {
        return PlaylistMapper.INSTANCE.convertToPlaylist(playlistDto);
    }
}
