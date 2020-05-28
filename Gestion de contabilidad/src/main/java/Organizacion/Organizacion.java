package Organizacion;

import java.util.List;

import Operaciones.Operacion;

public abstract class Organizacion {
    protected String nombreFicticio;
    protected List<Operacion> operaciones;
    
    public void AddOperacion(Operacion operacion){
    	operaciones.add(operacion);
    }
}
