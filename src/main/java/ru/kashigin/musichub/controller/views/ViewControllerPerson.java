package ru.kashigin.musichub.controller.views;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.service.PersonService;

import java.time.LocalDate;

@Controller
public class ViewControllerPerson {
    private final PersonService personService;

    public ViewControllerPerson(PersonService personService) {
        this.personService = personService;
    }
    @GetMapping("/people")
    public String viewPeople(Model model){
        model.addAttribute("persons", personService.getAllPersons());
        return "/per/persons";
    }

    @GetMapping("/people/new")
    public String addPerson(Model model){
        model.addAttribute("person", new Person());
        return "/per/addPerson";
    }

    @PostMapping("/people/new")
    public String addPersonSubmit(@ModelAttribute @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/per/addPerson";

        person.setRegistration(LocalDate.now()); //добавление даты регистрации
        personService.createPerson(person);

        return "redirect:/people";
    }

    @GetMapping("/people/{id}")
    public String viewPerson(@PathVariable("id") Long id, Model model){
        Person person = personService.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Person not found");
        model.addAttribute("person", person);
        return "/per/personDetails";
    }

    @GetMapping("/people/{id}/edit")
    public String editPerson(@PathVariable("id") Long id, Model model){
        Person person = personService.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Person not found");
        model.addAttribute("person", person);
        return "/per/editPerson";
    }

    @PostMapping("/people/{id}/edit")
    public String editPersonSubmit(@PathVariable("id") Long id, @ModelAttribute @Valid Person person,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/per/editPerson";
        if (personService.getPersonById(id) == null)
            throw new RuntimeException("Person not found");

        personService.updatePerson(id, person);
        return "redirect:/people/" + id;
    }

    @PostMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") Long id, @RequestParam("_method") String method){
        if ("delete".equalsIgnoreCase(method))
            personService.deletePerson(id);
        return "redirect:/people";
    }
}
