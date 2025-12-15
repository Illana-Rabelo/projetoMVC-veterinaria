package com.projeto.veterinaria.repository;

import com.projeto.veterinaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findTop5ByOrderByEstoqueAsc();

    List<Produto> findByEstoqueLessThanEqualOrderByEstoqueAsc(Integer estoque);
}
