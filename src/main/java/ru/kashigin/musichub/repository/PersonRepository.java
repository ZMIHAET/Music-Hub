package ru.kashigin.musichub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kashigin.musichub.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
