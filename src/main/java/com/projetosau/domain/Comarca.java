package com.projetosau.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comarca_tbl")
@Data
public class Comarca {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, message = "Houve um erro no cadastro do campo nome.")
    @NotBlank(message = "Nome é obrigatório")
    String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "regional_id")
    private Regional regional;

    @OneToMany(mappedBy = "comarca", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Unidade> unidades;


}