package br.ufrn.dimap.gestaovendas.mapper;

import br.ufrn.dimap.gestaovendas.domain.Carro;
import br.ufrn.dimap.gestaovendas.dto.CarroRequestDto;
import br.ufrn.dimap.gestaovendas.dto.CarroResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CarroMapper {

    /**
     * Mapeia um DTO de requisição para a entidade Carro.
     * Ignora o campo 'cliente', pois ele será tratado na camada de serviço.
     * Ignora os campos gerados pelo banco de dados.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "cliente", ignore = true) // Importante: ignoramos o objeto cliente
    })
    Carro toEntity(CarroRequestDto dto);

    /**
     * Mapeia a entidade Carro para um DTO de resposta.
     * Pega o ID de dentro do objeto 'cliente' e o mapeia para o campo 'clienteId' do DTO.
     */
    @Mapping(source = "cliente.id", target = "clienteId")
    CarroResponseDto toResponseDto(Carro entity);
}
