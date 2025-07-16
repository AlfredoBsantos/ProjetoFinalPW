package br.ufrn.dimap.gestaovendas.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class RevisaoResponseDto extends RepresentationModel<RevisaoResponseDto> {

    private Long id;
    private LocalDate dataRevisao;
    private String descricaoServico;
    private BigDecimal custo;
    private Long carroId;
}
