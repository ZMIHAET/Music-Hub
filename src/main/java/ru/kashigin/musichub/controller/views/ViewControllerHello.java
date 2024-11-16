package ru.kashigin.musichub.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewControllerHello {
    @GetMapping("/hello")
    public String Hello(){
        return "hello";
    }
}
