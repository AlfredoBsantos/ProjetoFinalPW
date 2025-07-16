package br.ufrn.dimap.gestaovendas.service;

import br.ufrn.dimap.gestaovendas.domain.Peca;
import br.ufrn.dimap.gestaovendas.repository.PecaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;

    public PecaService(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    @Transactional
    public Peca create(Peca peca) {
        return pecaRepository.save(peca);
    }

    @Transactional(readOnly = true)
    public Peca findById(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada com o id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Peca> findAll(Pageable pageable) {
        return pecaRepository.findAll(pageable);
    }

    @Transactional
    public Peca update(Long id, Peca pecaAtualizada) {
        Peca pecaExistente = findById(id); // Verifica se a peça existe

        // Atualiza os campos
        pecaExistente.setNome(pecaAtualizada.getNome());
        pecaExistente.setFabricante(pecaAtualizada.getFabricante());
        pecaExistente.setPreco(pecaAtualizada.getPreco());

        return pecaRepository.save(pecaExistente);
    }

    @Transactional
    public void delete(Long id) {
        Peca peca = findById(id); // Verifica se a peça existe antes de deletar
        pecaRepository.delete(peca);
    }
}
