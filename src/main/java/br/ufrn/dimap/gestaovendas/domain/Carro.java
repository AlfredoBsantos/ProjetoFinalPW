package br.ufrn.dimap.gestaovendas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE carro SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
public class Carro extends BaseEntity {

    @NotBlank(message = "A marca não pode estar em branco.")
    private String marca;

    @NotBlank(message = "O modelo não pode estar em branco.")
    private String modelo;

    private int ano;

    @NotBlank(message = "A placa não pode estar em branco.")
    @Column(unique = true)
    private String placa;

    // Relacionamento 1-para-N: Um Cliente (Pessoa) pode ter vários Carros.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Pessoa cliente;
}
