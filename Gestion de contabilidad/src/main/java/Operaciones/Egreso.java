package Operaciones;

import DatosDeOperaciones.DocumentoComercial;

import java.util.ArrayList;
import java.util.List;
import DatosDeOperaciones.Item;
import DatosDeOperaciones.MedioDePago;
import DatosDeOperaciones.Proveedor;

public class Egreso extends Operacion {
    private DocumentoComercial documentoComercial;
    private List<Item> items;
    private MedioDePago medioDePago;
    private Proveedor proveedor;

    public Egreso() {
        items = new ArrayList<>();
    }

    public double valorTotal(){
        //desarrollar
        return 0;
    }
}
