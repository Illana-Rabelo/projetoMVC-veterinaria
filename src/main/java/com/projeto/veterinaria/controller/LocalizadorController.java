package com.projeto.veterinaria.controller;

import com.projeto.veterinaria.model.Localizador;
import com.projeto.veterinaria.repository.LocalizadorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/localizadores")
public class LocalizadorController {

    private final LocalizadorRepository localizadorRepository;

    public LocalizadorController(LocalizadorRepository localizadorRepository) {
        this.localizadorRepository = localizadorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("localizadores", localizadorRepository.findAll());
        return "localizadores/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("localizador", new Localizador());
        return "localizadores/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Localizador localizador = localizadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Localizador não encontrado: " + id));
        model.addAttribute("localizador", localizador);
        return "localizadores/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Localizador localizador) {
        localizadorRepository.save(localizador);
        return "redirect:/localizadores";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        localizadorRepository.deleteById(id);
        return "redirect:/localizadores";
    }
}
