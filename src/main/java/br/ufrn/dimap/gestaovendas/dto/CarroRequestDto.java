package br.ufrn.dimap.gestaovendas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CarroRequestDto {

    @NotBlank(message = "A marca não pode estar em branco.")
    private String marca;

    @NotBlank(message = "O modelo não pode estar em branco.")
    private String modelo;

    @Positive(message = "O ano deve ser um número positivo.")
    private int ano;

    @NotBlank(message = "A placa não pode estar em branco.")
    private String placa;

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long clienteId; // Recebemos apenas o ID do cliente
}
