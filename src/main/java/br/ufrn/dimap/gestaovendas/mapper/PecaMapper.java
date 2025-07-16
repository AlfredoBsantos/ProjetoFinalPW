package br.ufrn.dimap.gestaovendas.mapper;

import br.ufrn.dimap.gestaovendas.domain.Peca;
import br.ufrn.dimap.gestaovendas.dto.PecaRequestDto;
import br.ufrn.dimap.gestaovendas.dto.PecaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PecaMapper {

    /**
     * Mapeia um DTO de requisição para a entidade Peca.
     * Ignora os campos gerados pelo banco de dados.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true)
    })
    Peca toEntity(PecaRequestDto dto);

    /**
     * Mapeia a entidade Peca para um DTO de resposta.
     */
    PecaResponseDto toResponseDto(Peca entity);
}
