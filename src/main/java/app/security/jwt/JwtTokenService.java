package app.security.jwt;


import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date;

@Log4j2
@Service
@PropertySource("classpath:jwt.properties")
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.normal}")
    private Long expiration_normal;

    @Value("${jwt.expiration.remember}")
    private Long expiration_remember;

//
    public String generateToken(Long user_id, boolean remember_me) {
        final Date now = new Date();
        final Date expiry = new Date(now.getTime() + (remember_me ? expiration_remember : expiration_normal));
        return Jwts.builder()
                .setSubject(user_id.toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String generateToken(Long user_id) {
        return generateToken(user_id, false);
    }


    public Long extractUserIdFromClaims(Jws<Claims> claimsJws) {
        return Long.parseLong(claimsJws.getBody().getSubject());
    }

    public Optional<Jws<Claims>> tokenToClaim(String token) {
        try {
            return Optional.of(Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token));
        } catch (SignatureException ex) {
            System.out.println("JWT: Invalid signature");
        } catch (MalformedJwtException ex) {
            System.out.println("JWT: Invalid token");
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT: Expired token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("JWT: Unsupported token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT: token is empty.");
        }
        return Optional.empty();
    }

    public boolean validateToken(String token) {
        return tokenToClaim(token).isPresent();
    }



}















