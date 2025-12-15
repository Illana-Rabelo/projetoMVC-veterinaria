package com.projeto.veterinaria.controller;

import com.projeto.veterinaria.dto.ProdutoMaisVendidoDTO;
import com.projeto.veterinaria.model.Produto;
import com.projeto.veterinaria.repository.ItemVendaRepository;
import com.projeto.veterinaria.repository.ProdutoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    private final ItemVendaRepository itemVendaRepository;
    private final ProdutoRepository produtoRepository;

    public RelatorioController(ItemVendaRepository itemVendaRepository, ProdutoRepository produtoRepository) {
        this.itemVendaRepository = itemVendaRepository;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public String index(
            @RequestParam(value = "estoqueMin", defaultValue = "5") Integer estoqueMin,
            Model model
    ) {
        // Relatório baseado no CRUD mestre/detalhe (Venda/ItemVenda/Produto)
        List<ProdutoMaisVendidoDTO> top5Vendidos =
                itemVendaRepository.buscarProdutosMaisVendidos(PageRequest.of(0, 5));

        // Relatório baseado no CRUD simples (Produto)
        List<Produto> produtosAbaixoOuIgual = produtoRepository.findByEstoqueLessThanEqualOrderByEstoqueAsc(estoqueMin);
        int totalAbaixoOuIgual = produtosAbaixoOuIgual.size();
        List<Produto> produtosAbaixoOuIgualExibicao = produtosAbaixoOuIgual.stream()
                .limit(10)
                .collect(Collectors.toList());

        model.addAttribute("top5Vendidos", top5Vendidos);
        model.addAttribute("estoqueMin", estoqueMin);
        model.addAttribute("totalAbaixoOuIgual", totalAbaixoOuIgual);
        model.addAttribute("produtosAbaixoOuIgual", produtosAbaixoOuIgualExibicao);

        return "relatorios/index";
    }
}
