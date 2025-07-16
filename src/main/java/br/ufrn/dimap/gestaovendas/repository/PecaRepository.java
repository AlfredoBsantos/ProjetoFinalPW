package br.ufrn.dimap.gestaovendas.repository;

import br.ufrn.dimap.gestaovendas.domain.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PecaRepository extends JpaRepository<Peca, Long> {
    // Métodos CRUD básicos serão gerados automaticamente pelo Spring Data JPA.
}
