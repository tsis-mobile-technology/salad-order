package com.tsis.salad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SaladController {

    @GetMapping("/salad/orderList")
    public String saladOrderList(Model model) {
        return "salad/orderList";
    }

    @GetMapping("/")
    public String saladRoot(Model model) {
        //if Admin Login을 했는지 체크 하고 login 유무는 세션
        //      했다면 '/salad/main' 화면으로 이동
        //else if Login을 하지 않았다면
        //      '/login'으로 이동 

        return new String();
    }
    
    
}
