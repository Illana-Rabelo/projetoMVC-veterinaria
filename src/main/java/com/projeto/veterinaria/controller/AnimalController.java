package com.projeto.veterinaria.controller;

import com.projeto.veterinaria.model.Animal;
import com.projeto.veterinaria.model.Tutor;
import com.projeto.veterinaria.repository.AnimalRepository;
import com.projeto.veterinaria.repository.TutorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalRepository animalRepository;
    private final TutorRepository tutorRepository;

    public AnimalController(AnimalRepository animalRepository, TutorRepository tutorRepository) {
        this.animalRepository = animalRepository;
        this.tutorRepository = tutorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("animais", animalRepository.findAll());
        return "animais/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        Animal animal = new Animal();
        animal.setTutorID(new Tutor());
        model.addAttribute("animal", animal);
        model.addAttribute("tutores", tutorRepository.findAll());
        return "animais/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado: " + id));
        if (animal.getTutorID() == null) {
            animal.setTutorID(new Tutor());
        }
        model.addAttribute("animal", animal);
        model.addAttribute("tutores", tutorRepository.findAll());
        return "animais/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Animal animal) {
        // Ajusta a referência do tutor para evitar inserir um Tutor "novo" com apenas ID.
        if (animal.getTutorID() != null && animal.getTutorID().getId() != null) {
            animal.setTutorID(tutorRepository.getReferenceById(animal.getTutorID().getId()));
        } else {
            animal.setTutorID(null);
        }
        animalRepository.save(animal);
        return "redirect:/animais";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        animalRepository.deleteById(id);
        return "redirect:/animais";
    }
}
