package ru.kashigin.musichub.controller;

import org.springframework.stereotype.Controller;
import ru.kashigin.musichub.service.PersonService;

@Controller
public class ViewControllerPerson {
    private final PersonService personService;

    public ViewControllerPerson(PersonService personService) {
        this.personService = personService;
    }
}
