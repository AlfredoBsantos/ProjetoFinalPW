package br.ufrn.dimap.gestaovendas;

import br.ufrn.dimap.gestaovendas.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class) // <-- VERIFIQUE SE ESTA LINHA ESTÁ AQUI
public class GestaoVendasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoVendasApplication.class, args);
    }
}