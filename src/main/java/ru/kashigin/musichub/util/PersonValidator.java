package ru.kashigin.musichub.util;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.service.PersonDetailsService;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;
    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        Person person = (Person)o;
        try {
            personDetailsService.loadUserByUsername(person.getName());
        } catch (UsernameNotFoundException ignored){
            return;
        }

        errors.rejectValue("name", "", "Человек с таким именем уже существует");
    }
}
