package Operaciones;

import DatosDeOperaciones.DocumentoComercial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    public void agregarItems(Item ... unosItems){
        Collections.addAll(this.items,unosItems);
    }
    public double valorTotal(){
        return  items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
        //Retorna la sumatoria del precio de cada item del egreso
    }
}
