package ru.kashigin.musichub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.musichub.dto.AuthenticationDto;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.security.JWTUtil;
import ru.kashigin.musichub.security.PersonDetails;
import ru.kashigin.musichub.service.PersonDetailsService;
import ru.kashigin.musichub.service.RegistrationService;
import ru.kashigin.musichub.service.rabbit.RegistrationProducer;
import ru.kashigin.musichub.util.PersonValidator;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final AuthenticationManager authenticationManager;
    private final PersonDetailsService personDetailsService;
    private final JWTUtil jwtUtil;
    private final RegistrationProducer registrationProducer;
    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person){
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";

        person.setRegistration(LocalDate.now()); //добавление даты регистрации

        registrationService.register(person);

        registrationProducer.sendRegistrationMessage("User registered: " + person.getName());

        return "redirect:/auth/login";
    }
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationDto authenticationDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.getName(), authenticationDto.getPassword())
        );
        final UserDetails userDetails = personDetailsService
                .loadUserByUsername(authenticationDto.getName());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("jwt-token", jwt));
    }
}
