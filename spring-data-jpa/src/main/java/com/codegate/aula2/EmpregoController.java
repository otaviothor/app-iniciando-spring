package com.codegate.aula2;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmpregoController {

  @Autowired
  EmpregoRepository empregoRepository;

  @RequestMapping("/")
  public String listaEmpregos(Model model) {
    model.addAttribute("empregos", empregoRepository.findAll());
    return "lista";
  }

  @GetMapping("/add")
  public String empregoForm(Model model) {
    model.addAttribute("emprego", new Emprego());
    return "empregoForm";
  }

  @PostMapping("/process")
  public String processForm(@Valid Emprego emprego, BindingResult result) {
    if (result.hasErrors())
      return "empregoForm";

    empregoRepository.save(emprego);
    return "redirect:/";
  }

  @GetMapping("/delete/{id}")
  public String deleteEmprego(@PathVariable Long id) {

    empregoRepository.deleteById(id);
    return "redirect:/";
  }
}
