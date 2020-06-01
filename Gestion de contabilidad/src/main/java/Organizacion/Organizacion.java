package Organizacion;

import java.util.ArrayList;
import java.util.List;

import Operaciones.Operacion;

public abstract class Organizacion {
    protected String nombreFicticio;
    protected ArrayList<Operacion> operaciones=new ArrayList<>();
    
    public void AddOperacion(Operacion operacion){
    	operaciones.add(operacion);
    }
}
