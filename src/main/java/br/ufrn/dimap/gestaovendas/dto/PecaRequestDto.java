package br.ufrn.dimap.gestaovendas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PecaRequestDto {

    @NotBlank(message = "O nome da peça não pode estar em branco.")
    private String nome;

    private String fabricante;

    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private BigDecimal preco;
}
