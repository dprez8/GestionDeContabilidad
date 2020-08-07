class ValidadorDeTransparencia{
    private ValidacionDeTransparencia validaciones = new ArrayList<>();
    private resultado;
    
    public void validarEgreso(egreso){
        resultado = validaciones.all(unaValidacion.validarEgreso(egreso));
        if(resultado){
            egreso.getRevisores().forEach(unRevisor=>unRevisor.getBandejaMensajes().add(new Mensaje("Paso todas las validaciones",egreso.id()));
        }
    }

}

}

class validarCantidadMinima{
    private int cantidadMinima = 2;

    public boolean validarEgreso(egreso){
        boolean resultado = egreso.getPresupuestos().length() >= cantidadMinima;
        if(!resultado)
            creadorMensaje.crearMensaje("No paso validar cantidad minima",egreso.getRevisores(),egreso.id());
      return resultado;
    }
}

class creadorMensaje{

    public void crearMensaje(String cuerpo, Estandar revisores,int id){
        revisores.forEach(unRevisor=>unRevisor.getBandejaMensajes().add(new Mensaje(cuerpo,id));
    }    
}