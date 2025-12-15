package com.projeto.veterinaria.controller;

import com.projeto.veterinaria.model.Produto;
import com.projeto.veterinaria.repository.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "produtos/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        Produto p = new Produto();
        if (p.getEstoque() == null) p.setEstoque(0);
        model.addAttribute("produto", p);
        return "produtos/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoRepository.findById(id).orElse(new Produto()));
        return "produtos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        if (produto.getEstoque() == null) produto.setEstoque(0);
        produtoRepository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/produtos";
    }
}
