package br.ufrn.dimap.gestaovendas.repository;

import br.ufrn.dimap.gestaovendas.domain.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<Carro, Long> {
    // O Spring Data JPA implementará os métodos CRUD básicos automaticamente.
    // Você pode adicionar métodos de busca customizados aqui se precisar,
    // por exemplo: findByPlaca(String placa);
}
