package ru.kashigin.musichub.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.repository.PersonRepository;
import ru.kashigin.musichub.security.PersonDetails;
import ru.kashigin.musichub.service.PersonDetailsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsServiceImpl implements PersonDetailsService {
    private final PersonRepository personRepository;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByName(name);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not Found");

        return new PersonDetails(person.get());
    }
}
