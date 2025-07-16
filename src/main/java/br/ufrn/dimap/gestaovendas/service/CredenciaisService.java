package br.ufrn.dimap.gestaovendas.service;

import br.ufrn.dimap.gestaovendas.repository.CredenciaisRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CredenciaisService implements UserDetailsService {

    private final CredenciaisRepository repository;

    public CredenciaisService(CredenciaisRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}
