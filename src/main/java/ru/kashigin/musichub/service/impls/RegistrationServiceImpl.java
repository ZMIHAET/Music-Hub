package ru.kashigin.musichub.service.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.repository.PersonRepository;
import ru.kashigin.musichub.service.RegistrationService;
import ru.kashigin.musichub.service.rabbit.Message;
import ru.kashigin.musichub.service.rabbit.RegistrationProducer;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationProducer registrationProducer;
    @Override
    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));

        personRepository.save(person);

        Message message = new Message(person.getPersonId(), person.getName());

        registrationProducer.sendRegistrationMessage(message);
    }
}
