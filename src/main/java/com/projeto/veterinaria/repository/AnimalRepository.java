package com.projeto.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.veterinaria.model.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {};