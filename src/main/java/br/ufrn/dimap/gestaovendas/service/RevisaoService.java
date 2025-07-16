package br.ufrn.dimap.gestaovendas.service;

import br.ufrn.dimap.gestaovendas.domain.Carro;
import br.ufrn.dimap.gestaovendas.domain.Peca;
import br.ufrn.dimap.gestaovendas.domain.Revisao;
import br.ufrn.dimap.gestaovendas.repository.CarroRepository;
import br.ufrn.dimap.gestaovendas.repository.PecaRepository;
import br.ufrn.dimap.gestaovendas.repository.RevisaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RevisaoService {

    private final RevisaoRepository revisaoRepository;
    private final CarroRepository carroRepository;
    private final PecaRepository pecaRepository; // Garanta que este repositório está sendo injetado

    // Construtor atualizado para incluir o PecaRepository
    public RevisaoService(RevisaoRepository revisaoRepository, CarroRepository carroRepository, PecaRepository pecaRepository) {
        this.revisaoRepository = revisaoRepository;
        this.carroRepository = carroRepository;
        this.pecaRepository = pecaRepository;
    }

    @Transactional
    public Revisao create(Revisao revisao, Long carroId) {
        Carro carro = carroRepository.findById(carroId)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado com o id: " + carroId));
        revisao.setCarro(carro);
        return revisaoRepository.save(revisao);
    }

    @Transactional(readOnly = true)
    public Revisao findById(Long id) {
        return revisaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revisão não encontrada com o id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Revisao> findAll(Pageable pageable) {
        return revisaoRepository.findAll(pageable);
    }

    @Transactional
    public Revisao update(Long id, Revisao revisaoAtualizada, Long carroId) {
        Revisao revisaoExistente = findById(id);
        Carro carro = carroRepository.findById(carroId)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado com o id: " + carroId));
        revisaoExistente.setCarro(carro);
        revisaoExistente.setDataRevisao(revisaoAtualizada.getDataRevisao());
        revisaoExistente.setDescricaoServico(revisaoAtualizada.getDescricaoServico());
        revisaoExistente.setCusto(revisaoAtualizada.getCusto());
        return revisaoRepository.save(revisaoExistente);
    }

    @Transactional
    public void delete(Long id) {
        Revisao revisao = findById(id);
        revisaoRepository.delete(revisao);
    }

    /**
     * Adiciona uma peça existente a uma revisão existente.
     */
    @Transactional
    public Revisao addPecaToRevisao(Long revisaoId, Long pecaId) {
        Revisao revisao = findById(revisaoId);
        // A chamada abaixo usa o pecaRepository, que agora está injetado corretamente
        Peca peca = pecaRepository.findById(pecaId)
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada com o id: " + pecaId));
        revisao.getPecas().add(peca);
        return revisaoRepository.save(revisao);
    }
}
