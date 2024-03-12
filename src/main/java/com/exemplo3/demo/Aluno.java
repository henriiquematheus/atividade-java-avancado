package com.exemplo3.demo;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data
@Entity
public class Aluno {

    @Id
    private Long matricula;

    private String nome;
    private int nota;

    public Aluno(Long matricula, String nome, int nota) {
        this.matricula = matricula;
        this.nome = nome;
        this.nota = nota;
    }


    public Aluno() {
    }

    }

