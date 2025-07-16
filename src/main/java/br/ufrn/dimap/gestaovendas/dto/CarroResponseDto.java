package br.ufrn.dimap.gestaovendas.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class CarroResponseDto extends RepresentationModel<CarroResponseDto> {

    private Long id;
    private String marca;
    private String modelo;
    private int ano;
    private String placa;
    private Long clienteId; // Enviamos de volta o ID do cliente para referência
}
