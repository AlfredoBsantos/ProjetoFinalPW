package br.ufrn.dimap.gestaovendas.repository;

import br.ufrn.dimap.gestaovendas.domain.Revisao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevisaoRepository extends JpaRepository<Revisao, Long> {
    // Métodos CRUD básicos serão gerados automaticamente.
}
