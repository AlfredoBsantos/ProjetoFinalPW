package br.ufrn.dimap.gestaovendas.controller;

import br.ufrn.dimap.gestaovendas.domain.Peca;
import br.ufrn.dimap.gestaovendas.dto.PecaRequestDto;
import br.ufrn.dimap.gestaovendas.dto.PecaResponseDto;
import br.ufrn.dimap.gestaovendas.mapper.PecaMapper;
import br.ufrn.dimap.gestaovendas.service.PecaService;
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
@RequestMapping("/pecas")
public class PecaController {

    private final PecaService pecaService;
    private final PecaMapper pecaMapper;

    public PecaController(PecaService pecaService, PecaMapper pecaMapper) {
        this.pecaService = pecaService;
        this.pecaMapper = pecaMapper;
    }

    /**
     * Endpoint para criar uma nova Peça.
     */
    @PostMapping
    public ResponseEntity<PecaResponseDto> create(@RequestBody @Valid PecaRequestDto requestDto) {
        Peca peca = pecaMapper.toEntity(requestDto);
        Peca pecaSalva = pecaService.create(peca);
        PecaResponseDto responseDto = pecaMapper.toResponseDto(pecaSalva);

        responseDto.add(linkTo(methodOn(PecaController.class).findById(responseDto.getId())).withSelfRel());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    /**
     * Endpoint para buscar uma Peça pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PecaResponseDto> findById(@PathVariable Long id) {
        Peca peca = pecaService.findById(id);
        PecaResponseDto responseDto = pecaMapper.toResponseDto(peca);

        responseDto.add(linkTo(methodOn(PecaController.class).findById(id)).withSelfRel());
        responseDto.add(linkTo(methodOn(PecaController.class).findAll(Pageable.unpaged())).withRel("allPecas"));

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Endpoint para listar todas as Peças de forma paginada.
     */
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PecaResponseDto>>> findAll(@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<Peca> pecaPage = pecaService.findAll(pageable);
        Page<PecaResponseDto> dtoPage = pecaPage.map(pecaMapper::toResponseDto);

        List<EntityModel<PecaResponseDto>> pecaModels = dtoPage.getContent().stream()
                .map(dto -> {
                    dto.add(linkTo(methodOn(PecaController.class).findById(dto.getId())).withSelfRel());
                    return EntityModel.of(dto);
                })
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                dtoPage.getSize(),
                dtoPage.getNumber(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages());

        PagedModel<EntityModel<PecaResponseDto>> pagedModel = PagedModel.of(pecaModels, metadata,
                linkTo(methodOn(PecaController.class).findAll(pageable)).withSelfRel());

        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Endpoint para atualizar uma Peça existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PecaResponseDto> update(@PathVariable Long id, @RequestBody @Valid PecaRequestDto requestDto) {
        Peca pecaParaAtualizar = pecaMapper.toEntity(requestDto);
        Peca pecaAtualizada = pecaService.update(id, pecaParaAtualizar);
        PecaResponseDto responseDto = pecaMapper.toResponseDto(pecaAtualizada);

        responseDto.add(linkTo(methodOn(PecaController.class).findById(id)).withSelfRel());

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Endpoint para deletar uma Peça.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pecaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
