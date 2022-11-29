package com.example.caderno.dataprovider.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tag")
@Getter
@Setter
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_id")
    private NotaEntity notaEntity;

}
