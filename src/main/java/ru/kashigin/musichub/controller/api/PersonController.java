package ru.kashigin.musichub.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.dto.PersonDto;
import ru.kashigin.musichub.dto.PlaylistDto;
import ru.kashigin.musichub.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    @GetMapping
    public List<PersonDto> getAll(){
        return personService.getAllPersonsApi();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getById(@PathVariable Long id) {
        return personService.getPersonByIdApi(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PersonDto create(@RequestBody @Valid PersonDto dto){
        return personService.createPerson(dto);
    }

    @PutMapping("/{id}")
    public PersonDto update(@PathVariable Long id, @RequestBody @Valid PersonDto dto){
        return personService.updatePerson(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        personService.deletePerson(id);
    }
}
