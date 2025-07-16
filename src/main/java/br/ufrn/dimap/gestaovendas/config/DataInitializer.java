package br.ufrn.dimap.gestaovendas.config;

import br.ufrn.dimap.gestaovendas.domain.Credenciais;
import br.ufrn.dimap.gestaovendas.domain.Pessoa;
import br.ufrn.dimap.gestaovendas.repository.CredenciaisRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitializer implements CommandLineRunner {

    // ... o construtor permanece o mesmo ...
    private final CredenciaisRepository credenciaisRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(CredenciaisRepository credenciaisRepository, BCryptPasswordEncoder passwordEncoder) {
        this.credenciaisRepository = credenciaisRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional // Adicione @Transactional para garantir que tudo ocorra em uma única transação
    public void run(String... args) throws Exception {
        if (credenciaisRepository.count() == 0) {
            // 1. Cria uma nova Pessoa (sem salvar ainda)
            Pessoa pessoaAdmin = new Pessoa();
            pessoaAdmin.setNome("Administrador");

            // 2. Cria as Credenciais, já associando a Pessoa
            Credenciais credenciaisAdmin = new Credenciais();
            credenciaisAdmin.setUsername("admin");
            credenciaisAdmin.setPassword(passwordEncoder.encode("12345"));
            credenciaisAdmin.setRole("ROLE_ADMIN,ROLE_USER");
            credenciaisAdmin.setPessoa(pessoaAdmin);

            // 3. Salva APENAS as credenciais. A Pessoa será salva em cascata.
            credenciaisRepository.save(credenciaisAdmin);

            System.out.println(">>> Usuário de teste 'admin' criado com senha '12345'");
        }
    }
}
