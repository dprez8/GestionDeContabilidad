package Domain.Controllers.jwt;

import Domain.Entities.Usuarios.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Date;

public class TokenService {
    private static final long EXPIRATION_TIME = 10 * 60 * 1000l; // 10 minutes
    private static final String ROLES = "roles";

    private final String jwtSecretKey;

    private final BlacklistedTokenRepository blacklistedTokenRepository = new BlacklistedTokenRepository();

    public TokenService(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public final void removeExpired() {
        blacklistedTokenRepository.removeExpired();
    }

    public final String newToken(Usuario user) {
        DefaultClaims claims = new DefaultClaims();
        claims.setSubject(new Integer(user.getId()).toString());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public final void revokeToken(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        blacklistedTokenRepository.addToken(token, expirationDate.getTime());
    }

    /**
     * throws ExpiredJwtException if token has expired
     *
     * @param token
     * @return
     */
    public final String getUserPrincipal(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();

        /**Debo retornar un usuario de la BD*/
        //return UserPrincipal.of(claims.getSubject(), roles.stream().map(role -> Role.valueOf(role)).collect(Collectors.toList()));
        return claims.getSubject();
    }

    public final boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.isTokenBlacklisted(token);
    }

    public final boolean validateToken(String token) {
        if (!isTokenBlacklisted(token)) {
            try {
                getUserPrincipal(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

}
