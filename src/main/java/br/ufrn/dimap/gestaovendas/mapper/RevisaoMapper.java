package br.ufrn.dimap.gestaovendas.mapper;

import br.ufrn.dimap.gestaovendas.domain.Revisao;
import br.ufrn.dimap.gestaovendas.dto.RevisaoRequestDto;
import br.ufrn.dimap.gestaovendas.dto.RevisaoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RevisaoMapper {

    /**
     * Mapeia um DTO de requisição para a entidade Revisao.
     * Ignora o campo 'carro', pois ele será buscado e associado na camada de serviço.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "carro", ignore = true) // Ignora o objeto carro
    })
    Revisao toEntity(RevisaoRequestDto dto);

    /**
     * Mapeia a entidade Revisao para um DTO de resposta.
     * Extrai o ID do carro para o campo 'carroId' do DTO.
     */
    @Mapping(source = "carro.id", target = "carroId")
    RevisaoResponseDto toResponseDto(Revisao entity);
}
