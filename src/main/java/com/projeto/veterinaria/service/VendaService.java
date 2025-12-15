package com.projeto.veterinaria.service;

import com.projeto.veterinaria.model.ItemVenda;
import com.projeto.veterinaria.model.Produto;
import com.projeto.veterinaria.model.Venda;
import com.projeto.veterinaria.repository.ItemVendaRepository;
import com.projeto.veterinaria.repository.ProdutoRepository;
import com.projeto.veterinaria.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemVendaRepository itemVendaRepository;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository, ItemVendaRepository itemVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.itemVendaRepository = itemVendaRepository;
    }

    @Transactional
    public void adicionarItem(Venda venda, Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (produto.getEstoque() == null) {
            produto.setEstoque(0);
        }
        if (produto.getEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto selecionado.");
        }

        ItemVenda item = new ItemVenda();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());

        venda.addItem(item);

        // baixa de estoque
        produto.setEstoque(produto.getEstoque() - quantidade);

        produtoRepository.save(produto);
        vendaRepository.save(venda);
    }

    @Transactional
    public void removerItem(Venda venda, ItemVenda item) {
        Produto produto = item.getProduto();
        if (produto != null) {
            Integer estoqueAtual = produto.getEstoque() == null ? 0 : produto.getEstoque();
            Integer qtd = item.getQuantidade() == null ? 0 : item.getQuantidade();
            produto.setEstoque(estoqueAtual + qtd);
            produtoRepository.save(produto);
        }

        venda.removeItem(item);
        vendaRepository.save(venda);
        itemVendaRepository.delete(item);
    }

    @Transactional
    public void excluirVendaRestaurandoEstoque(Venda venda) {
        for (ItemVenda item : venda.getItens()) {
            Produto produto = item.getProduto();
            if (produto != null) {
                Integer estoqueAtual = produto.getEstoque() == null ? 0 : produto.getEstoque();
                Integer qtd = item.getQuantidade() == null ? 0 : item.getQuantidade();
                produto.setEstoque(estoqueAtual + qtd);
                produtoRepository.save(produto);
            }
        }
        vendaRepository.delete(venda);
    }
}
