package Domain.Operaciones.Egreso;

import Domain.DatosDeOperaciones.DocumentoComercial;
import Domain.DatosDeOperaciones.ItemEgreso;
import Domain.DatosDeOperaciones.MedioDePago;
import Domain.DatosDeOperaciones.Proveedor;
import Domain.Organizacion.Organizacion;

import java.util.Date;
import java.util.List;

public interface IBuilderEgreso {

    public IBuilderEgreso agregarProveedor(Proveedor proveedor);
    public IBuilderEgreso agregarFechaOperacion(Date fecha);
    public IBuilderEgreso agregarMedioDePago(MedioDePago medioDePago);
    public IBuilderEgreso agregarDocumentoComercial(DocumentoComercial documentoComercial);
    public IBuilderEgreso agregarDatosOrganizacion(Organizacion organizacion);
    public IBuilderEgreso agregarItems(ItemEgreso ... unosItems);
    public Egreso build();
    public void reset();

}
