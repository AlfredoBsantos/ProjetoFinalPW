package br.ufrn.dimap.gestaovendas.controller;

import br.ufrn.dimap.gestaovendas.domain.Pessoa;
import br.ufrn.dimap.gestaovendas.dto.PessoaRequestDto;
import br.ufrn.dimap.gestaovendas.dto.PessoaResponseDto;
import br.ufrn.dimap.gestaovendas.service.PessoaService;
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
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService service;

    // Construtor sem o PessoaMapper
    public PessoaController(PessoaService service) {
        this.service = service;
    }

    // --- MÉTODOS DE CONVERSÃO MANUAL ---

    // Converte a Entidade para o DTO de Resposta
    private PessoaResponseDto toResponseDto(Pessoa pessoa) {
        PessoaResponseDto dto = new PessoaResponseDto();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome()); // Mapeamento manual do nome
        return dto;
    }

    // Converte o DTO de Requisição para a Entidade
    private Pessoa toEntity(PessoaRequestDto requestDto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(requestDto.getNome());
        return pessoa;
    }

    // --- ENDPOINTS ---

    @PostMapping
    public ResponseEntity<PessoaResponseDto> create(@RequestBody @Valid PessoaRequestDto requestDto) {
        Pessoa pessoa = toEntity(requestDto);
        Pessoa pessoaSalva = service.create(pessoa);
        PessoaResponseDto responseDto = toResponseDto(pessoaSalva);

        responseDto.add(linkTo(methodOn(PessoaController.class).findById(responseDto.getId())).withSelfRel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDto> findById(@PathVariable Long id) {
        Pessoa pessoa = service.findById(id);
        PessoaResponseDto responseDto = toResponseDto(pessoa);

        responseDto.add(linkTo(methodOn(PessoaController.class).findById(id)).withSelfRel());
        responseDto.add(linkTo(methodOn(PessoaController.class).findAll(Pageable.unpaged())).withRel("allPessoas"));
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PessoaResponseDto>>> findAll(@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<Pessoa> pessoaPage = service.findAll(pageable);
        Page<PessoaResponseDto> dtoPage = pessoaPage.map(this::toResponseDto);

        List<EntityModel<PessoaResponseDto>> pessoaModels = dtoPage.getContent().stream()
                .map(dto -> {
                    dto.add(linkTo(methodOn(PessoaController.class).findById(dto.getId())).withSelfRel());
                    return EntityModel.of(dto);
                })
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                dtoPage.getSize(), dtoPage.getNumber(), dtoPage.getTotalElements(), dtoPage.getTotalPages());

        PagedModel<EntityModel<PessoaResponseDto>> pagedModel = PagedModel.of(pessoaModels, metadata,
                linkTo(methodOn(PessoaController.class).findAll(pageable)).withSelfRel());

        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDto> update(@PathVariable Long id, @RequestBody @Valid PessoaRequestDto requestDto) {
        Pessoa pessoaParaAtualizar = toEntity(requestDto);
        Pessoa pessoaAtualizada = service.update(id, pessoaParaAtualizar);
        PessoaResponseDto responseDto = toResponseDto(pessoaAtualizada);

        responseDto.add(linkTo(methodOn(PessoaController.class).findById(id)).withSelfRel());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
