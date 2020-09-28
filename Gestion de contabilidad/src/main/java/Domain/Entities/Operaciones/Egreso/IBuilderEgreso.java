package Domain.Entities.Operaciones.Egreso;

import Domain.Entities.DatosDeOperaciones.DocumentoComercial;
import Domain.Entities.DatosDeOperaciones.ItemEgreso;
import Domain.Entities.DatosDeOperaciones.Pago;
import Domain.Entities.DatosDeOperaciones.Proveedor;
import Domain.Entities.Organizacion.Organizacion;

import java.time.LocalDate;

public interface IBuilderEgreso {

    public IBuilderEgreso agregarProveedor(Proveedor proveedor);
    public IBuilderEgreso agregarFechaOperacion(LocalDate fecha);
    public IBuilderEgreso agregarPago(Pago pago);
    public IBuilderEgreso agregarDocumentoComercial(DocumentoComercial documentoComercial);
    public IBuilderEgreso agregarDatosOrganizacion(Organizacion organizacion);
    public IBuilderEgreso agregarItems(ItemEgreso ... unosItems);
    public Egreso build();
    public void reset();

}
