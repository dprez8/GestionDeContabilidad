package Domain.Entities.Operaciones.Egreso;

import Domain.Entities.DatosDeOperaciones.DocumentoComercial;
import Domain.Entities.DatosDeOperaciones.ItemEgreso;
import Domain.Entities.DatosDeOperaciones.Pago;
import Domain.Entities.DatosDeOperaciones.Proveedor;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Exceptions.ExcepcionCreacionEgreso;
import Domain.Exceptions.contraseniaCorta;

import java.time.LocalDate;
import java.util.List;

public class BuilderEgresoConcreto implements IBuilderEgreso {

    private Egreso egreso;
    
    public BuilderEgresoConcreto() {
        this.egreso = new Egreso();
    }

    @Override
    public IBuilderEgreso agregarProveedor(Proveedor proveedor) {
        this.egreso.setProveedor(proveedor);
        return this;
    }
    
    @Override
    public IBuilderEgreso agregarFechaOperacion(LocalDate fecha) {
        this.egreso.setFechaOperacion(fecha);
        return this;
    }

    @Override
    public IBuilderEgreso agregarPago(Pago pago) {
        this.egreso.setPago(pago);
        return this;
    }
    
    @Override
    public IBuilderEgreso agregarCantidadPresupuestos(int cantidad){
    	this.egreso.setCantidadPresupuestos(cantidad);
    	return this;
    }
    
    @Override
    public IBuilderEgreso agregarDocumentoComercial(DocumentoComercial documentoComercial) {
        this.egreso.setDocumento(documentoComercial);
        return this;
    }
        
    @Override
    public IBuilderEgreso agregarDatosOrganizacion(Organizacion organizacion) {
        this.egreso.setOrganizacion(organizacion);
        return this;
    }
    
    @Override
    public IBuilderEgreso agregarItems(ItemEgreso ... unosItems) {
        this.egreso.addItems(unosItems);
        return this;
    }

    @Override
    public IBuilderEgreso agregarItems(List<ItemEgreso> items) throws ExcepcionCreacionEgreso {
        if(items.isEmpty())
            throw new ExcepcionCreacionEgreso("Falta agregar items al egreso");
        this.egreso.setItems(items);
        return this;
    }
    
    @Override
    public Egreso build() throws ExcepcionCreacionEgreso {
        if(this.egreso.getItems().isEmpty())
            throw new ExcepcionCreacionEgreso("Falta agregar items al egreso");
        Double valorTotal = this.egreso.calcularValorTotal();
        this.egreso.setValorTotal(valorTotal);
        this.egreso.setFechaCarga();
        return this.egreso;
    }

    @Override
    public void reset() {
        this.egreso = new Egreso();
    }
}
