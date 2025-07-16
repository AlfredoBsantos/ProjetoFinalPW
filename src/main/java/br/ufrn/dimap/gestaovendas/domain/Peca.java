package br.ufrn.dimap.gestaovendas.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE peca SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
public class Peca extends BaseEntity {

    @NotBlank(message = "O nome da peça não pode estar em branco.")
    private String nome;

    private String fabricante;

    private BigDecimal preco;
}
