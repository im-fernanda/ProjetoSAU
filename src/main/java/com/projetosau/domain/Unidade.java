package com.projetosau.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "unidade_tbl")
@Data
public class Unidade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 2, message = "Houve um erro no cadastro do campo nome.")
    @NotBlank(message = "Nome é obrigatório")
    String nome;

    @ManyToOne
    @JoinColumn(name = "comarca_id", nullable = false)
    @JsonBackReference // Evita serialização cíclica
    private Comarca comarca;


    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Equipamento> equipamento;


    public Regional getRegional() {
        return this.comarca != null ? this.comarca.getRegional() : null;
    }

    public void setRegional(Regional regional) {
        if (this.comarca != null) {
            this.comarca.setRegional(regional);
        }
    }



}
