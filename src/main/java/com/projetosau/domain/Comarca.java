package com.projetosau.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "comarca_tbl")
@Data
public class Comarca implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 2, message = "Houve um erro no cadastro do campo nome.")
    @NotBlank(message = "Nome é obrigatório")
    String nome;

    @ManyToOne
    @JoinColumn(name = "regional_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @JsonBackReference // Evita serialização cíclica
    private Regional regional;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "comarca", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private Set<Unidade> unidades = new HashSet<>();

    @Override
    public String toString() {
        return "Comarca [id=" + id + ", nome=" + nome + "]";
    }

}