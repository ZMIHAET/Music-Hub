package ru.kashigin.musichub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kashigin.musichub.model.Song;

public interface SongRepository extends JpaRepository<Song, Long> {
}
