package com.projetosau.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "equipamento_tbl")
@Data
public class Equipamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "O tombo é obrigatório")
    Integer tombo;

    @Size(min = 2, message = "Houve um erro no cadastro do campo nome.")
    @NotBlank(message = "Nome é obrigatório")
    String nome;

    @NotBlank(message = "Modelo é obrigatório")
    String modelo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    Unidade unidade;

    String descricao;

}