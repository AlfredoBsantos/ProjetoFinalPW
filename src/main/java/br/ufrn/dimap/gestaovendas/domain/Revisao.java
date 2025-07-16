package br.ufrn.dimap.gestaovendas.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet; // Importe o HashSet
import java.util.Set;    // Importe o Set

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE revisao SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
public class Revisao extends BaseEntity {

    private LocalDate dataRevisao;

    @Column(length = 500)
    private String descricaoServico;

    private BigDecimal custo;

    // Relacionamento 1-para-N: Um Carro pode ter várias Revisões.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id")
    private Carro carro;

    // Relacionamento N-para-N: Uma Revisão pode ter várias Peças
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "revisao_peca", // Nome da tabela de associação
            joinColumns = @JoinColumn(name = "revisao_id"),
            inverseJoinColumns = @JoinColumn(name = "peca_id")
    )
    private Set<Peca> pecas = new HashSet<>();
}
