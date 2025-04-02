package ru.kashigin.musichub.service.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

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
    public void createPlaylist(Playlist playlist) {
        if (playlist.getOwner() == null || playlist.getOwner().getPersonId() == null)
            playlist.setOwner(null);
        playlistRepository.save(playlist);
    }

    @Override
    public PlaylistDto createPlaylist(PlaylistDto playlistDto) {
        Playlist playlist = convertToPlaylist(playlistDto);

        checkOwner(playlistDto, playlist);

        Playlist saved = playlistRepository.save(playlist);
        return convertToPlaylistDto(saved);
    }

    @Override
    public void updatePlaylist(Long id, Playlist playlist) {
        Optional<Playlist> existingPlaylist = getPlaylistById(id);
        if (existingPlaylist.isPresent()){
            existingPlaylist.get().setName(playlist.getName());
            existingPlaylist.get().setDescription(playlist.getDescription());


            if (playlist.getOwner() == null || playlist.getOwner().getPersonId() == null)
                existingPlaylist.get().setOwner(null);

            playlistRepository.save(existingPlaylist.get());
        }
    }
    @Override
    public PlaylistDto updatePlaylist(Long id, PlaylistDto playlistDto) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found"));

        playlist.setName(playlistDto.getName());
        playlist.setDescription(playlistDto.getDescription());

        checkOwner(playlistDto, playlist);

        Playlist updated = playlistRepository.save(playlist);
        return convertToPlaylistDto(updated);
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

    @Override
    public PlaylistDto convertToPlaylistDto(Playlist playlist) {
        return PlaylistMapper.INSTANCE.convertToPlaylistDto(playlist);
    }

    @Override
    public List<PlaylistDto> getAllPlaylistsApi() {
        return playlistRepository.findAll().stream()
                .map(this::convertToPlaylistDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PlaylistDto> getPlaylistByIdApi(Long id) {
        return playlistRepository.findById(id)
                .map(this::convertToPlaylistDto);
    }

    private void checkOwner(PlaylistDto playlistDto, Playlist playlist){
        if (playlistDto.getOwner() != null && playlistDto.getOwner().getPersonId() != null) {
            Person person = personRepository.findById(playlistDto.getOwner().getPersonId())
                    .orElseThrow(() -> new EntityNotFoundException("Person not found"));
            playlist.setOwner(person);
        }
    }

}
