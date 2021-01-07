package pl.brzezinski.web_quiz_service.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.brzezinski.web_quiz_service.exceptions.WebQuizException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try{
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourcesAsStream = getClass().getResourceAsStream("/keystore.jks");
            keyStore.load(resourcesAsStream, "password".toCharArray());
        }catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
            throw new WebQuizException("Exception occurred while loading keystore");
        }
    }

    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("keystore", "password".toCharArray());
        }catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new WebQuizException("Exception occurred while retrieving public key from keystore");
        }
    }
}
