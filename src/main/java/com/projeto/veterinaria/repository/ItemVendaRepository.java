package com.projeto.veterinaria.repository;

import com.projeto.veterinaria.dto.ProdutoMaisVendidoDTO;
import com.projeto.veterinaria.model.ItemVenda;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    @Query("""
        select new com.projeto.veterinaria.dto.ProdutoMaisVendidoDTO(
            i.produto.id,
            i.produto.descricao,
            sum(i.quantidade)
        )
        from ItemVenda i
        group by i.produto.id, i.produto.descricao
        order by sum(i.quantidade) desc
    """)
    List<ProdutoMaisVendidoDTO> buscarProdutosMaisVendidos(Pageable pageable);
}
