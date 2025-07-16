package br.ufrn.dimap.gestaovendas.repository;

import br.ufrn.dimap.gestaovendas.domain.Credenciais;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CredenciaisRepository extends JpaRepository<Credenciais, Long> {
    Optional<Credenciais> findByUsername(String username);
}
