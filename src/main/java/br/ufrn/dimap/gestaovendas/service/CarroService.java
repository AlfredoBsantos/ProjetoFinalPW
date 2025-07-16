package br.ufrn.dimap.gestaovendas.service;

import br.ufrn.dimap.gestaovendas.domain.Carro;
import br.ufrn.dimap.gestaovendas.domain.Pessoa;
import br.ufrn.dimap.gestaovendas.repository.CarroRepository;
import br.ufrn.dimap.gestaovendas.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarroService {

    private final CarroRepository carroRepository;
    private final PessoaRepository pessoaRepository; // Para buscar o cliente

    public CarroService(CarroRepository carroRepository, PessoaRepository pessoaRepository) {
        this.carroRepository = carroRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Carro create(Carro carro, Long clienteId) {
        // 1. Busca o cliente (Pessoa) pelo ID fornecido
        Pessoa cliente = pessoaRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o id: " + clienteId));

        // 2. Associa o cliente encontrado ao carro
        carro.setCliente(cliente);

        // 3. Salva o carro com o cliente associado
        return carroRepository.save(carro);
    }

    @Transactional(readOnly = true)
    public Carro findById(Long id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado com o id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Carro> findAll(Pageable pageable) {
        return carroRepository.findAll(pageable);
    }

    @Transactional
    public Carro update(Long id, Carro carroAtualizado, Long clienteId) {
        Carro carroExistente = findById(id); // Reusa o findById para verificar se o carro existe

        // Busca e atualiza o cliente
        Pessoa cliente = pessoaRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o id: " + clienteId));
        carroExistente.setCliente(cliente);

        // Atualiza os outros campos do carro
        carroExistente.setMarca(carroAtualizado.getMarca());
        carroExistente.setModelo(carroAtualizado.getModelo());
        carroExistente.setAno(carroAtualizado.getAno());
        carroExistente.setPlaca(carroAtualizado.getPlaca());

        return carroRepository.save(carroExistente);
    }

    @Transactional
    public void delete(Long id) {
        Carro carro = findById(id); // Verifica se o carro existe antes de deletar
        carroRepository.delete(carro); // O @SQLDelete cuidará do soft delete
    }
}
