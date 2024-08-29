package com.projetosau.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "equipamento_tbl")
@Data
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotNull(message = "O tombo é obrigatório")
    @Column(unique = true, nullable = false)
    Integer tombo;

    @Size(min = 2, message = "Houve um erro no cadastro do campo nome.")
    @NotBlank(message = "Nome é obrigatório")
    String nome;

    @NotBlank(message = "Modelo é obrigatório")
    String modelo;

    @NotBlank(message = "Unidade é obrigatória")
    String unidade;

    String descricao;

}