package Domain.Controllers.jwt;

import Domain.Entities.Usuarios.Usuario;
import spark.Request;

public abstract class AbstractTokenController {
    private static final String TOKEN_PREFIX = "Bearer";

    private final TokenService tokenService;

    public AbstractTokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    protected Usuario getUserDesdeToken(Request request) {
        String authorizationHeader = request.headers("Authorization");
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");
        return tokenService.getUser(token);
    }

    /*
    protected UserPrincipal getUserPrincipal(Request request) {
        String authorizationHeader = request.headers("Authorization");
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");
        return tokenService.getUserPrincipal(token);
    }

    protected boolean hasRole(Request request, Role[] roles) {
        if (roles.length == 0) {
            return true;
        }
        List<Role> userRoles = getUserPrincipal(request).getRoles();
        return userRoles.stream().filter(Arrays.asList(roles)::contains).findAny().isPresent();
    }
     */
}
