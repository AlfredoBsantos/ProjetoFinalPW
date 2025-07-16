package br.ufrn.dimap.gestaovendas.service;

import br.ufrn.dimap.gestaovendas.domain.Pessoa;
import br.ufrn.dimap.gestaovendas.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Pessoa create(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    @Transactional(readOnly = true)
    public Pessoa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com o id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Pessoa> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Método de atualização ajustado para receber apenas a entidade Pessoa.
     */
    @Transactional
    public Pessoa update(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoaExistente = findById(id); // Reusa o findById para verificar se a pessoa existe

        // Atualiza os campos da pessoa existente com os novos dados
        pessoaExistente.setNome(pessoaAtualizada.getNome());
        // Adicione aqui a atualização de outros campos, se houver

        return repository.save(pessoaExistente);
    }

    @Transactional
    public void delete(Long id) {
        Pessoa pessoa = findById(id); // Verifica se a pessoa existe antes de deletar
        repository.delete(pessoa); // O @SQLDelete cuidará do soft delete
    }
}
