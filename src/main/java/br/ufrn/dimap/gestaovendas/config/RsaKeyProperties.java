package br.ufrn.dimap.gestaovendas.config; // Verifique se o pacote está correto

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@ConfigurationProperties(prefix = "rsa") // [cite: 1833]
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}