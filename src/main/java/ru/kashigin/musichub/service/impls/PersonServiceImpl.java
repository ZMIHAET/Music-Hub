package ru.kashigin.musichub.service.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.dto.PersonDto;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.repository.PersonRepository;
import ru.kashigin.musichub.service.PersonService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        Person existingPerson = getPersonById(id);
        if (existingPerson != null) {
            existingPerson.setName(person.getName());
            existingPerson.setEmail(person.getEmail());
            existingPerson.setPassword(person.getPassword());

            return personRepository.save(existingPerson);
        }
        return null;
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Person convertToPerson(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }
}
