package br.ufrn.dimap.gestaovendas.controller;

import br.ufrn.dimap.gestaovendas.domain.Carro;
import br.ufrn.dimap.gestaovendas.dto.CarroRequestDto;
import br.ufrn.dimap.gestaovendas.dto.CarroResponseDto;
import br.ufrn.dimap.gestaovendas.mapper.CarroMapper;
import br.ufrn.dimap.gestaovendas.service.CarroService;
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
@RequestMapping("/carros")
public class CarroController {

    private final CarroService carroService;
    private final CarroMapper carroMapper;

    public CarroController(CarroService carroService, CarroMapper carroMapper) {
        this.carroService = carroService;
        this.carroMapper = carroMapper;
    }

    /**
     * Endpoint para criar um novo Carro.
     */
    @PostMapping
    public ResponseEntity<CarroResponseDto> create(@RequestBody @Valid CarroRequestDto requestDto) {
        Carro carro = carroMapper.toEntity(requestDto);
        Carro carroSalvo = carroService.create(carro, requestDto.getClienteId());
        CarroResponseDto responseDto = carroMapper.toResponseDto(carroSalvo);

        responseDto.add(linkTo(methodOn(CarroController.class).findById(responseDto.getId())).withSelfRel());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    /**
     * Endpoint para buscar um Carro pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarroResponseDto> findById(@PathVariable Long id) {
        Carro carro = carroService.findById(id);
        CarroResponseDto responseDto = carroMapper.toResponseDto(carro);

        responseDto.add(linkTo(methodOn(CarroController.class).findById(id)).withSelfRel());
        responseDto.add(linkTo(methodOn(CarroController.class).findAll(Pageable.unpaged())).withRel("allCarros"));
        responseDto.add(linkTo(methodOn(PessoaController.class).findById(responseDto.getClienteId())).withRel("cliente"));


        return ResponseEntity.ok(responseDto);
    }

    /**
     * Endpoint para listar todos os Carros de forma paginada.
     */
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CarroResponseDto>>> findAll(@PageableDefault(size = 10, sort = "modelo") Pageable pageable) {
        Page<Carro> carroPage = carroService.findAll(pageable);
        Page<CarroResponseDto> dtoPage = carroPage.map(carroMapper::toResponseDto);

        List<EntityModel<CarroResponseDto>> carroModels = dtoPage.getContent().stream()
                .map(dto -> {
                    dto.add(linkTo(methodOn(CarroController.class).findById(dto.getId())).withSelfRel());
                    dto.add(linkTo(methodOn(PessoaController.class).findById(dto.getClienteId())).withRel("cliente"));
                    return EntityModel.of(dto);
                })
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                dtoPage.getSize(),
                dtoPage.getNumber(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages());

        PagedModel<EntityModel<CarroResponseDto>> pagedModel = PagedModel.of(carroModels, metadata,
                linkTo(methodOn(CarroController.class).findAll(pageable)).withSelfRel());

        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Endpoint para atualizar um Carro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarroResponseDto> update(@PathVariable Long id, @RequestBody @Valid CarroRequestDto requestDto) {
        Carro carroParaAtualizar = carroMapper.toEntity(requestDto);
        Carro carroAtualizado = carroService.update(id, carroParaAtualizar, requestDto.getClienteId());
        CarroResponseDto responseDto = carroMapper.toResponseDto(carroAtualizado);

        responseDto.add(linkTo(methodOn(CarroController.class).findById(id)).withSelfRel());

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Endpoint para deletar um Carro (soft delete).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
