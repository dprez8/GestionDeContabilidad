package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.EgresoRequest;
import Domain.Controllers.DTO.ItemRequest;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/*********************************************/
/**POST
        /api/operaciones/egreso
        Request
        {
        "fecha": "2010-01-01",
        "proveedor": 2,
        "medioDePago": {
            "id": 1,
            "dato": "2095034a"
        },
        "documentoComercial": {
        "tipo": 2,
        "numeroDocumento": 202020,
        "fechaDePedido": "2010-01-01",
        "fechaDeEntrega": "2010-01-01",
        "descripcion": "Lorem ipsum dolor sit amet",
        "imagenDelDocumento": ""
        },
        "items": [
        {
        "nombreProducto": "Coca cola",
        "precio": 2500.0,
        "cantidad": 57
        }
        ]
        }
        Response
        {
        "code": 200,
        "message": "asd",
        "data": {
        "id": 4
        }
    }
/*****************************************************************/
public class OperacionesRestController {
    private Repositorio<Egreso> repoEgresos;
    private Repositorio<ItemEgreso> repoItems;
    private Repositorio<Producto> repoProductos;
    private Repositorio<Pago> repoPagos;
    private Repositorio<MedioDePago> repoMedioDePagos;
    private Repositorio<Proveedor> repoProveedores;
    private Repositorio<DocumentoComercial> repoDocumentos;
    private Repositorio<TipoDocumento> repoTipoDocumento;

    public OperacionesRestController(){
        this.repoEgresos         = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.repoItems           = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        this.repoProductos       = new Repositorio<>(new DaoHibernate<>(Producto.class));
        this.repoPagos           = new Repositorio<>(new DaoHibernate<>(Pago.class));
        this.repoMedioDePagos    = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));
        this.repoProveedores     = new Repositorio<>(new DaoHibernate<>(Proveedor.class));
        this.repoDocumentos      = new Repositorio<>(new DaoHibernate<>(DocumentoComercial.class));
        this.repoTipoDocumento   = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
    }


    public String cargarNuevoEgreso(Request request, Response response) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();
        EgresoRequest egresoRequest = gson.fromJson(request.body(),EgresoRequest.class);
        Egreso egreso = asignarEgresoDesde(egresoRequest);
        return "";
    }

    private Egreso asignarEgresoDesde (EgresoRequest egresoRequest) {
        try {

            Proveedor proveedor         = this.repoProveedores.buscar(egresoRequest.proveedor);
            MedioDePago medioDePago     = this.repoMedioDePagos.buscar(egresoRequest.medioDePago.id);
            TipoDocumento tipoDocumento = this.repoTipoDocumento.buscar(egresoRequest.documentoComercial.tipo);


            Pago pago = new Pago();
            pago.setMedioDePago(medioDePago);
            pago.setNumeroAsociado(egresoRequest.medioDePago.dato);

            DocumentoComercial documentoComercial = new DocumentoComercial();
            documentoComercial.setTipo(tipoDocumento);
            documentoComercial.setNumDocumento(egresoRequest.documentoComercial.numeroDocumento);
            documentoComercial.setFechaDePedido(egresoRequest.documentoComercial.fechaDePedido);
            documentoComercial.setFechaDeEntrega(egresoRequest.documentoComercial.fechaDeEntrega);
            documentoComercial.setDescripcion(egresoRequest.documentoComercial.descripcion);

            List<Producto> productos = buscarProductosDisponibles();
            List<ItemEgreso> items = egresoRequest.items
                                            .stream()
                                            .map(item->mapearItem(item,productos))
                                            .collect(Collectors.toList());

            Egreso egreso = new BuilderEgresoConcreto()
                    .agregarFechaOperacion(egresoRequest.fecha)
                    .agregarProveedor(proveedor)
                    .agregarPago(pago)
                    .agregarDocumentoComercial(documentoComercial)
                    .build();

            //Modificar items con el egreso
            return egreso;
        }
        catch (NullPointerException ex){
            return null;
        }
    }
    private ItemEgreso mapearItem(ItemRequest itemRequest, List<Producto> productos) {
        ItemEgreso itemEgreso = new ItemEgreso();
        itemEgreso.setPrecio(itemRequest.precio);
        itemEgreso.setCantidad(itemRequest.cantidad);

        if(!productos.isEmpty()) {
            Optional<Producto> producto = productos
                                            .stream()
                                            .filter(unProducto ->
                                                    unProducto.getNombreProducto().equals(itemRequest.nombreProducto)).findFirst();
            if(producto.isPresent()) {
                itemEgreso.setProducto(producto.get());
            }
            else {
                Producto productoNuevo = new Producto();
                productoNuevo.setNombreProducto(itemRequest.nombreProducto);

                this.repoProductos.agregar(productoNuevo);

                itemEgreso.setProducto(productoNuevo);
            }
        }
        else {
            Producto producto = new Producto();
            producto.setNombreProducto(itemRequest.nombreProducto);

            this.repoProductos.agregar(producto);

            itemEgreso.setProducto(producto);
        }
        return  itemEgreso;
    }

    private List<Producto> buscarProductosDisponibles() {
        try {
            List<Producto> productos = this.repoProductos.buscarTodos();
            return productos;
        }
        catch (NullPointerException ex) {
            return null;
        }
    }

}
