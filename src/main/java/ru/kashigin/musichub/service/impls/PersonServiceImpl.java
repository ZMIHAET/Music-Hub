package ru.kashigin.musichub.service.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.dto.PersonDto;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.repository.PersonRepository;
import ru.kashigin.musichub.service.PersonService;
import ru.kashigin.musichub.service.mappers.PersonMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Optional<PersonDto> getPersonByIdApi(Long id) {
        return personRepository.findById(id)
                .map(this::convertToPersonDto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createPerson(Person person) {
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        Person person = convertToPerson(personDto);
        person.setRole("ROLE_USER");

        Person saved = personRepository.save(person);
        return convertToPersonDto(saved);
    }


    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updatePerson(Long id, Person person) {
        Optional<Person> existingPerson = getPersonById(id);
        if (existingPerson.isPresent()) {
            existingPerson.get().setName(person.getName());
            existingPerson.get().setEmail(person.getEmail());
            existingPerson.get().setPassword(person.getPassword());

            personRepository.save(existingPerson.get());
        }
    }

    @Override
    public PersonDto updatePerson(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        person.setName(personDto.getName());
        person.setEmail(personDto.getEmail());
        person.setPassword(personDto.getPassword());

        Person updated = personRepository.save(person);

        return convertToPersonDto(updated);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Person convertToPerson(PersonDto personDto) {
        return PersonMapper.INSTANCE.convertToPerson(personDto);
    }

    @Override
    public PersonDto convertToPersonDto(Person person) {
        return PersonMapper.INSTANCE.convertToPersonDto(person);
    }

    @Override
    public List<PersonDto> getAllPersonsApi() {
        return personRepository.findAll().stream()
                .map(this::convertToPersonDto)
                .collect(Collectors.toList());
    }

}
