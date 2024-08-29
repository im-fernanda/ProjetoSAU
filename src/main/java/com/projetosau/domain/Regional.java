package com.projetosau.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "regional_tbl")
@Data
public class Regional {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Size(min = 2, message = "Houve um erro no cadastro do campo nome.")
    @NotBlank(message = "Nome é obrigatório")
    String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comarca_id")
    private Comarca comarca;
}
