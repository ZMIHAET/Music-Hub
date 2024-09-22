package ru.kashigin.musichub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kashigin.musichub.model.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
