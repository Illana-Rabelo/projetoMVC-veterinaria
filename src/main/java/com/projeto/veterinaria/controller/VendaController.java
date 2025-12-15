package com.projeto.veterinaria.controller;

import com.projeto.veterinaria.model.Cliente;
import com.projeto.veterinaria.model.ItemVenda;
import com.projeto.veterinaria.model.Produto;
import com.projeto.veterinaria.model.Venda;
import com.projeto.veterinaria.repository.ClienteRepository;
import com.projeto.veterinaria.repository.ItemVendaRepository;
import com.projeto.veterinaria.repository.ProdutoRepository;
import com.projeto.veterinaria.repository.VendaRepository;
import com.projeto.veterinaria.service.VendaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemVendaRepository itemVendaRepository;
    private final VendaService vendaService;

    public VendaController(VendaRepository vendaRepository,
                           ClienteRepository clienteRepository,
                           ProdutoRepository produtoRepository,
                           ItemVendaRepository itemVendaRepository,
                           VendaService vendaService) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.itemVendaRepository = itemVendaRepository;
        this.vendaService = vendaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vendas", vendaRepository.findAll());
        return "vendas/list";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        Venda v = new Venda();
        v.setData(LocalDate.now());
        model.addAttribute("venda", v);
        model.addAttribute("clientes", clienteRepository.findAll());
        return "vendas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Venda venda,
                         @RequestParam("clienteId") Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        venda.setCliente(cliente);
        if (venda.getData() == null) {
            venda.setData(LocalDate.now());
        }
        Venda saved = vendaRepository.save(venda);
        return "redirect:/vendas/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Venda venda = vendaRepository.findById(id).orElse(null);
        if (venda == null) {
            return "redirect:/vendas";
        }
        model.addAttribute("venda", venda);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        return "vendas/detail";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizarCabecalho(@PathVariable Long id,
                                    @RequestParam("clienteId") Long clienteId,
                                    @RequestParam("data") String data) {
        Venda venda = vendaRepository.findById(id).orElse(null);
        if (venda == null) return "redirect:/vendas";
        venda.setCliente(clienteRepository.findById(clienteId).orElse(null));
        venda.setData(LocalDate.parse(data));
        vendaRepository.save(venda);
        return "redirect:/vendas/" + id;
    }

    @PostMapping("/{id}/itens/adicionar")
    public String adicionarItem(@PathVariable Long id,
                                @RequestParam("produtoId") Long produtoId,
                                @RequestParam("quantidade") Integer quantidade,
                                RedirectAttributes ra) {
        Venda venda = vendaRepository.findById(id).orElse(null);
        Produto produto = produtoRepository.findById(produtoId).orElse(null);

        if (venda == null || produto == null) {
            ra.addFlashAttribute("erro", "Venda ou produto não encontrado.");
            return "redirect:/vendas/" + id;
        }

        try {
            vendaService.adicionarItem(venda, produto, quantidade == null ? 0 : quantidade);
            ra.addFlashAttribute("sucesso", "Item adicionado com sucesso.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("erro", ex.getMessage());
        }

        return "redirect:/vendas/" + id;
    }

    @GetMapping("/{vendaId}/itens/excluir/{itemId}")
    public String excluirItem(@PathVariable Long vendaId,
                              @PathVariable Long itemId,
                              RedirectAttributes ra) {
        Venda venda = vendaRepository.findById(vendaId).orElse(null);
        ItemVenda item = itemVendaRepository.findById(itemId).orElse(null);
        if (venda == null || item == null) {
            ra.addFlashAttribute("erro", "Item ou venda não encontrado.");
            return "redirect:/vendas/" + vendaId;
        }
        vendaService.removerItem(venda, item);
        ra.addFlashAttribute("sucesso", "Item removido.");
        return "redirect:/vendas/" + vendaId;
    }

    @GetMapping("/excluir/{id}")
    public String excluirVenda(@PathVariable Long id, RedirectAttributes ra) {
        Venda venda = vendaRepository.findById(id).orElse(null);
        if (venda != null) {
            vendaService.excluirVendaRestaurandoEstoque(venda);
            ra.addFlashAttribute("sucesso", "Venda excluída.");
        }
        return "redirect:/vendas";
    }
}
