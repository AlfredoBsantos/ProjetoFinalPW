package br.ufrn.dimap.gestaovendas.mapper;

import br.ufrn.dimap.gestaovendas.domain.Pessoa;
import br.ufrn.dimap.gestaovendas.dto.PessoaRequestDto;
import br.ufrn.dimap.gestaovendas.dto.PessoaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    /**
     * Mapeia um DTO de requisição para a entidade Pessoa.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true)
    })
    Pessoa toEntity(PessoaRequestDto dto);

    /**
     * Mapeia a entidade Pessoa para um DTO de resposta.
     * Mapeia explicitamente os campos para garantir a conversão.
     */
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nome", target = "nome")
    })
    PessoaResponseDto toResponseDto(Pessoa entity);
}
