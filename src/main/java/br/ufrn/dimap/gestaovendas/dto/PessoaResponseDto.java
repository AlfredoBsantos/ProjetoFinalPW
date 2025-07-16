package br.ufrn.dimap.gestaovendas.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO para enviar os dados de uma Pessoa na resposta da API.
 * Estende RepresentationModel para suportar links HATEOAS.
 */
@Data
@EqualsAndHashCode(callSuper = false) // Importante para evitar conflito com RepresentationModel
public class PessoaResponseDto extends RepresentationModel<PessoaResponseDto> {

    private Long id;
    private String nome;

    // Adicione aqui outros campos que você quer enviar na resposta
    // Exemplo:
    // private int idade;
    // private String sexo;
    // private EnderecoResponseDto endereco;
}
