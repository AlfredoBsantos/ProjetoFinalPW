// Dentro de src/main/java/br/ufrn/dimap/gestaovendas/repository/PessoaRepository.java
package br.ufrn.dimap.gestaovendas.repository;

import br.ufrn.dimap.gestaovendas.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}