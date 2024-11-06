package ru.kashigin.musichub.service.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.dto.PersonDto;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.repository.PersonRepository;
import ru.kashigin.musichub.service.PersonService;
import ru.kashigin.musichub.service.mappers.PersonMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        Optional<Person> existingPerson = getPersonById(id);
        if (existingPerson.isPresent()) {
            existingPerson.get().setName(person.getName());
            existingPerson.get().setEmail(person.getEmail());
            existingPerson.get().setPassword(person.getPassword());

            return personRepository.save(existingPerson.get());
        }
        return null;
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Person convertToPerson(PersonDto personDto) {
        return PersonMapper.INSTANCE.convertToPerson(personDto);
    }
}
