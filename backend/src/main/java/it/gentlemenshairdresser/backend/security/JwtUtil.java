package it.gentlemenshairdresser.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.gentlemenshairdresser.backend.entities.Cliente;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY ="u3JdCq8z9m3x5yVdO8qC7nq6wWk2cV1zQkR9gqL7p0s=";

    private final long EXPIRATION_TIME = 1000*60*60; //La durata del token è di 1 ora

    public String generateToken(Cliente cliente) {
        return Jwts.builder()
                .setSubject(cliente.getEmail()) // 3. Imposta l'identificativo dell'utente (la sua email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 4. Data di emissione
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 5. Data di scadenza
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 6. Firma il token con la chiave segreta
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
