package com.example.caderno.dataprovider.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="nota")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String titulo;

    @Column
    private String nota;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    public NotaEntity(String titulo, String nota) {
        this.titulo = titulo;
        this.nota = nota;
        this.dataCriacao = LocalDateTime.now();
    }
}
