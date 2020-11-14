package Domain.Controllers.DTO;

import Domain.Entities.Organizacion.Organizacion;
import com.google.gson.annotations.Expose;

public class UsuarioResponse {
        @Expose
        public Integer code;
        @Expose
        public String message;
        @Expose
        public Organizacion organizacion;
        @Expose
        public String nombre;
}
