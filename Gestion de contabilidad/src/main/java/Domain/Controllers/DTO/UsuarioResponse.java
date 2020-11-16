package Domain.Controllers.DTO;

import com.google.gson.annotations.Expose;

public class UsuarioResponse {
        @Expose
        public Integer code;
        @Expose
        public String message;
        @Expose
        public String nombre;
        @Expose
        public String username;
        @Expose
        public String apellido;
        @Expose
        public String gmail;
}
