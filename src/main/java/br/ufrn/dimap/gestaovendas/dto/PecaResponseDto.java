package br.ufrn.dimap.gestaovendas.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class PecaResponseDto extends RepresentationModel<PecaResponseDto> {

    private Long id;
    private String nome;
    private String fabricante;
    private BigDecimal preco;
}
