package com.tsis.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SaladController {

    @GetMapping("/salad")
    public String salad() {
        return "/cms/saladOrder";
    }
}
