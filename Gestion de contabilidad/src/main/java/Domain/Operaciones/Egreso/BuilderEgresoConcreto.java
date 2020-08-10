package Domain.Operaciones.Egreso;

import Domain.DatosDeOperaciones.DocumentoComercial;
import Domain.DatosDeOperaciones.ItemEgreso;
import Domain.DatosDeOperaciones.MedioDePago;
import Domain.DatosDeOperaciones.Proveedor;
import Domain.Organizacion.Organizacion;

import java.util.Date;
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
    public IBuilderEgreso agregarFechaOperacion(Date fecha) {
        this.egreso.setFechaOperacion(fecha);
        return this;
    }

    @Override
    public IBuilderEgreso agregarMedioDePago(MedioDePago medioDePago) {
        this.egreso.setMedioDePago(medioDePago);
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
    public Egreso build() {
        return this.egreso;
    }

    @Override
    public void reset() {
        this.egreso = new Egreso();
    }
}
