package br.ufrn.dimap.gestaovendas.controller;

import br.ufrn.dimap.gestaovendas.domain.Revisao;
import br.ufrn.dimap.gestaovendas.dto.RevisaoRequestDto;
import br.ufrn.dimap.gestaovendas.dto.RevisaoResponseDto;
import br.ufrn.dimap.gestaovendas.mapper.RevisaoMapper;
import br.ufrn.dimap.gestaovendas.service.RevisaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/revisoes")
public class RevisaoController {

    private final RevisaoService revisaoService;
    private final RevisaoMapper revisaoMapper;

    public RevisaoController(RevisaoService revisaoService, RevisaoMapper revisaoMapper) {
        this.revisaoService = revisaoService;
        this.revisaoMapper = revisaoMapper;
    }

    /**
     * Endpoint para criar uma nova Revisão.
     */
    @PostMapping
    public ResponseEntity<RevisaoResponseDto> create(@RequestBody @Valid RevisaoRequestDto requestDto) {
        Revisao revisao = revisaoMapper.toEntity(requestDto);
        Revisao revisaoSalva = revisaoService.create(revisao, requestDto.getCarroId());
        RevisaoResponseDto responseDto = revisaoMapper.toResponseDto(revisaoSalva);

        responseDto.add(linkTo(methodOn(RevisaoController.class).findById(responseDto.getId())).withSelfRel());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    /**
     * Endpoint para buscar uma Revisão pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RevisaoResponseDto> findById(@PathVariable Long id) {
        Revisao revisao = revisaoService.findById(id);
        RevisaoResponseDto responseDto = revisaoMapper.toResponseDto(revisao);

        responseDto.add(linkTo(methodOn(RevisaoController.class).findById(id)).withSelfRel());
        responseDto.add(linkTo(methodOn(RevisaoController.class).findAll(Pageable.unpaged())).withRel("allRevisoes"));
        responseDto.add(linkTo(methodOn(CarroController.class).findById(responseDto.getCarroId())).withRel("carro"));

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Endpoint para listar todas as Revisões de forma paginada.
     */
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<RevisaoResponseDto>>> findAll(@PageableDefault(size = 10, sort = "dataRevisao") Pageable pageable) {
        Page<Revisao> revisaoPage = revisaoService.findAll(pageable);
        Page<RevisaoResponseDto> dtoPage = revisaoPage.map(revisaoMapper::toResponseDto);

        List<EntityModel<RevisaoResponseDto>> revisaoModels = dtoPage.getContent().stream()
                .map(dto -> {
                    dto.add(linkTo(methodOn(RevisaoController.class).findById(dto.getId())).withSelfRel());
                    dto.add(linkTo(methodOn(CarroController.class).findById(dto.getCarroId())).withRel("carro"));
                    return EntityModel.of(dto);
                })
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                dtoPage.getSize(),
                dtoPage.getNumber(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages());

        PagedModel<EntityModel<RevisaoResponseDto>> pagedModel = PagedModel.of(revisaoModels, metadata,
                linkTo(methodOn(RevisaoController.class).findAll(pageable)).withSelfRel());

        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Endpoint para atualizar uma Revisão existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RevisaoResponseDto> update(@PathVariable Long id, @RequestBody @Valid RevisaoRequestDto requestDto) {
        Revisao revisaoParaAtualizar = revisaoMapper.toEntity(requestDto);
        Revisao revisaoAtualizada = revisaoService.update(id, revisaoParaAtualizar, requestDto.getCarroId());
        RevisaoResponseDto responseDto = revisaoMapper.toResponseDto(revisaoAtualizada);

        responseDto.add(linkTo(methodOn(RevisaoController.class).findById(id)).withSelfRel());

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Endpoint para deletar uma Revisão.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        revisaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para associar uma Peça a uma Revisão.
     */
    @PostMapping("/{revisaoId}/pecas/{pecaId}")
    public ResponseEntity<RevisaoResponseDto> addPeca(@PathVariable Long revisaoId, @PathVariable Long pecaId) {
        Revisao revisaoAtualizada = revisaoService.addPecaToRevisao(revisaoId, pecaId);
        RevisaoResponseDto responseDto = revisaoMapper.toResponseDto(revisaoAtualizada);

        // Adiciona os links HATEOAS relevantes
        responseDto.add(linkTo(methodOn(RevisaoController.class).findById(revisaoId)).withSelfRel());
        responseDto.add(linkTo(methodOn(CarroController.class).findById(responseDto.getCarroId())).withRel("carro"));

        return ResponseEntity.ok(responseDto);
    }
}
