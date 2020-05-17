package Exceptions;

public class contraseniaMuyComun extends Exception {
    public contraseniaMuyComun() {
        super("La contrasenia es muy comun, dentro del top10k peores, pensar algo mas complejo");
    }
}
