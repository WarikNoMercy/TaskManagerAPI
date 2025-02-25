package kirill.task.api.config;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import kirill.task.api.model.User;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtConfig {

    @Value("${jwt.token.expirationtime}")
    private int expTime;

    private String key;

    public JwtConfig(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGen.generateKey();
            key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            //return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());
        //System.out.println(claims);
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + expTime))
                .signWith(getKey())
                .compact();
    }

//    public SecretKey genKey(){
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey secretKey = keyGen.generateKey();
//            String key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String getNameFromToken(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload()
                .get("username", String.class);
        // return "";
    }
}
