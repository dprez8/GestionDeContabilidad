package Domain.Controllers.jwt;

import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

public class TokenService {
    private static long EXPIRATION_TIME;
    private static final String ROL = "rol";
    private static final Logger LOG = Logger.getLogger(TokenService.class.getName());

    private final String jwtSecretKey;

    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private Repositorio<Usuario> repoUsuarios;

    public TokenService(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
        this.blacklistedTokenRepository = new BlacklistedTokenRepository();
        this.repoUsuarios               = new Repositorio<>(new DaoHibernate<>(Usuario.class));
        setExpirationTime();
    }

    public final void removeExpired() {
        blacklistedTokenRepository.removeExpired();
    }

    public final String newToken(Usuario user) {
        DefaultClaims claims = new DefaultClaims();
        claims.setSubject(new Integer(user.getId()).toString());
        if(user.getClass().equals(Administrador.class))
            claims.put(ROL,"Administrador");
        else
            claims.put(ROL,"Estandar");
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
     * @return Usuario or null
     */
    public final Usuario getUser(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
            Usuario usuario = this.repoUsuarios.buscar(new Integer(claims.getSubject()));
            return usuario;
        }catch (Exception ex) {
            return null;
        }
    }
    /**
     * throws ExpiredJwtException if token has expired
     *
     * @param token
     * @return String
     */
    public final String getRol(String token) {
        Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        return (String) claims.get(ROL);
    }

    public final boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.isTokenBlacklisted(token);
    }

    public final boolean validateToken(String token) {
        if (!isTokenBlacklisted(token)) {
            if(getUser(token) != null){
                return true;
            }
        }
        return false;
    }

    private void setExpirationTime() {
        Properties prop=new Properties();
        try {
            prop.load(new FileReader("src/main/resources/config/config.properties"));
            EXPIRATION_TIME = Long.parseLong(prop.getProperty("EXPIRATION_TIME"));
        }
        catch (IOException ex) {
            LOG.warning("No se encontro el archivo 'config.properties' o el valor de EXPIRATION_TIME");
        }
        return;
    }

}
