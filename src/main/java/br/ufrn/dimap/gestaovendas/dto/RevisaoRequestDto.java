package br.ufrn.dimap.gestaovendas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RevisaoRequestDto {

    @NotNull(message = "A data da revisão é obrigatória.")
    private LocalDate dataRevisao;

    @NotBlank(message = "A descrição do serviço não pode estar em branco.")
    private String descricaoServico;

    @NotNull(message = "O custo é obrigatório.")
    @Positive(message = "O custo deve ser um valor positivo.")
    private BigDecimal custo;

    @NotNull(message = "O ID do carro é obrigatório.")
    private Long carroId;
}
