package ru.kashigin.musichub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kashigin.musichub.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
