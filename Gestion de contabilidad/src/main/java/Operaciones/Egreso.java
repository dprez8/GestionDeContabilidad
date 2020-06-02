package Operaciones;

import java.util.ArrayList;

import java.util.Date;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import DatosDeOperaciones.*;
import Organizacion.EntidadJuridica;

public class Egreso extends Operacion {
	
	private DocumentoComercial documento;
	private List<ItemEgreso> items = new ArrayList<>();
	private MedioDePago medioDePago;
	private Proveedor proveedor;

	public Egreso(DocumentoComercial documento,
				  MedioDePago medioDePago, Proveedor proveedor, EntidadJuridica organizacion,
				  ItemEgreso ...items) {
		this.fechaOperacion = new Date();
		this.documento = documento;
		this.medioDePago = medioDePago;
		this.proveedor = proveedor;
		this.organizacion = organizacion;
		this.agregarItems(items);
	}

    public void agregarItems (ItemEgreso ... unosItems) {
        Collections.addAll(this.items, unosItems);
    }

    public double valorTotal () {
        return  items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
        // Retorna la sumatoria del precio de cada item del egreso
    }
}