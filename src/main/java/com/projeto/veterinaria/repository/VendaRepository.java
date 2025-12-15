package com.projeto.veterinaria.repository;

import com.projeto.veterinaria.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
