package ru.kashigin.musichub.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kashigin.musichub.model.Playlist;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
