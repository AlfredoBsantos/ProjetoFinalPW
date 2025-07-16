package br.ufrn.dimap.gestaovendas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para receber os dados de criação/atualização de uma Pessoa.
 * A anotação @Data do Lombok cria os getters e setters automaticamente.
 */
@Data
public class PessoaRequestDto {

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String nome;
}
