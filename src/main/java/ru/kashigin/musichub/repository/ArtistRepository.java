package ru.kashigin.musichub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kashigin.musichub.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
