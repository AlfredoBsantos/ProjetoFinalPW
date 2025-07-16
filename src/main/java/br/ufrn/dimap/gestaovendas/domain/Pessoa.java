// Dentro de src/main/java/br/ufrn/dimap/gestaovendas/domain/Pessoa.java
package br.ufrn.dimap.gestaovendas.domain;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE pessoa SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
public class Pessoa extends BaseEntity {
    private String nome;
    // ... outros atributos e relacionamentos (1-1, 1-N, N-N)
}