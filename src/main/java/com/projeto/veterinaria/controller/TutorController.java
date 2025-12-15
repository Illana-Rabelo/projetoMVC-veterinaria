package com.projeto.veterinaria.controller;

import com.projeto.veterinaria.model.Tutor;
import com.projeto.veterinaria.repository.TutorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tutores")
public class TutorController {

    private final TutorRepository tutorRepository;

    public TutorController(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tutores", tutorRepository.findAll());
        return "tutores/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("tutor", new Tutor());
        return "tutores/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor não encontrado: " + id));
        model.addAttribute("tutor", tutor);
        return "tutores/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Tutor tutor) {
        tutorRepository.save(tutor);
        return "redirect:/tutores";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        tutorRepository.deleteById(id);
        return "redirect:/tutores";
    }
}
