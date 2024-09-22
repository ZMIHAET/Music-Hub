package ru.kashigin.musichub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kashigin.musichub.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
