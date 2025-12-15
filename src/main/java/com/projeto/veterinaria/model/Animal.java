package com.projeto.veterinaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "Animal")
public class Animal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String raca;
    private String idade;
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutorID;
    private String ultimaLoc;
public Animal() {
}

public Animal(Long id, String nome, String raca, String idade, Tutor tutorID, String ultimaLoc) {
    this.id = id;
    this.nome = nome;
    this.raca = raca;
    this.idade = idade;
    this.tutorID = tutorID;
    this.ultimaLoc = ultimaLoc;
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

public String getRaca() {
    return raca;
}

public void setRaca(String raca) {
    this.raca = raca;
}

public String getIdade() {
    return idade;
}

public void setIdade(String idade) {
    this.idade = idade;
}

public Tutor getTutorID() {
    return tutorID;
}

public void setTutorID(Tutor tutorID) {
    this.tutorID = tutorID;
}

public String getUltimaLoc() {
    return ultimaLoc;
}

public void setUltimaLoc(String ultimaLoc) {
    this.ultimaLoc = ultimaLoc;
}
}
