package com.codegate.aula1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

  @RequestMapping("/")
  public String home(Model model) {
    model.addAttribute("mensagem", "Esta foi uma mensagem injetada através do model");
    return "index";
  }
}
