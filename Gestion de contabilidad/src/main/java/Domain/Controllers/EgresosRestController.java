package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.*;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Estandar;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/*****************************************************************/
public class EgresosRestController {

    private Repositorio<EntidadJuridica> repoEntidadJuridica;
    private Respuesta respuesta;
    private Gson gson;

    public EgresosRestController(){
        this.repoEntidadJuridica = new Repositorio<>(new DaoHibernate<>(EntidadJuridica.class));
        this.respuesta           = new Respuesta();
    }


    public String cargarNuevoEgreso(Request request, Response response) {

        String jsonResponse;

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        EgresoRequest egresoRequest = null;

        try {
            egresoRequest = this.gson.fromJson(request.body(),EgresoRequest.class);
        } catch (Exception exception) {
            this.respuesta.setCode(400);
            this.respuesta.setMessage("Formato incorrecto en datos del egreso");
            String jsonRespuesta = this.gson.toJson(this.respuesta);
            response.body(jsonRespuesta);
            return response.body();
        }

        EntidadJuridica entidadJuridica= this.repoEntidadJuridica.buscar(usuario.getMiOrganizacion().getId());

        Egreso egreso = asignarEgresoDesde(egresoRequest, entidadJuridica);

        if(egreso == null) {
            this.respuesta.setCode(400);
            this.respuesta.setMessage("Problema al cargar el egreso");
            jsonResponse = this.gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }
        this.respuesta.setCode(200);
        this.respuesta.setMessage("Egreso cargado exitosamente");

        CargaEgresoResponse cargaEgresoResponse = new CargaEgresoResponse();

        cargaEgresoResponse.code    = this.respuesta.getCode();
        cargaEgresoResponse.message = this.respuesta.getMessage();
        cargaEgresoResponse.id = egreso.getId();
        jsonResponse = this.gson.toJson(cargaEgresoResponse);
        response.body(jsonResponse);

        return response.body();
    }

    public String listadoDeEgresos(Request request, Response response) {

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        EntidadJuridica entidadJuridica = this.repoEntidadJuridica.buscar(usuario.getMiOrganizacion().getId());
        List<EgresoResponse> egresosAEnviar;
        String jsonResponse;

        egresosAEnviar = entidadJuridica.getEgresos()
                    .stream()
                    .map(this::mapearEgreso)
                    .collect(Collectors.toList());

        if (egresosAEnviar.isEmpty()) {
            this.respuesta.setCode(404);
            this.respuesta.setMessage("No hay egresos cargados");
            jsonResponse = this.gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }

        ListadoEgresos listadoEgresos = new ListadoEgresos();
        listadoEgresos.code    = 200;
        listadoEgresos.message = "Ok";
        listadoEgresos.egresos = egresosAEnviar;

        jsonResponse = this.gson.toJson(listadoEgresos);
        response.body(jsonResponse);
        egresosAEnviar.clear();

        return response.body();
    }

    public String mostrarEgreso(Request request, Response response) {

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

        String jsonResponse;
        Egreso egreso;

        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .serializeNulls()
                .create();

        Repositorio<Egreso> repoEgresos = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        egreso = repoEgresos.buscar(new Integer(request.params("egresoId")));

        if(egreso == null) {
            Gson gson = new Gson();
            this.respuesta.setCode(404);
            this.respuesta.setMessage("El egreso no existe");
            jsonResponse = gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }

        EgresoDetalladoResponse egresoDetallado = new EgresoDetalladoResponse();
        egresoDetallado.code         = 200;
        egresoDetallado.message      = "Ok";
        egresoDetallado.egreso       = egreso;
        egresoDetallado.estaSuscrito = verificarSuscripcion(usuario,egreso);

        jsonResponse = this.gson.toJson(egresoDetallado);
        response.body(jsonResponse);

        return response.body();
    }

    private Egreso asignarEgresoDesde (EgresoRequest egresoRequest, EntidadJuridica entidadJuridica) {
        Proveedor proveedor;
        MedioDePago medioDePago;
        TipoDocumento tipoDocumento;

        Repositorio<Proveedor> repoProveedores   = new Repositorio<>(new DaoHibernate<>(Proveedor.class));
        Repositorio<TipoDocumento> repoTipoDocumento = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
        Repositorio<MedioDePago> repoMediosDePagos = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));

        try {
             proveedor     = repoProveedores.buscar(egresoRequest.proveedor);
             medioDePago   = repoMediosDePagos.buscar(egresoRequest.medioDePago.id);
             tipoDocumento = repoTipoDocumento.buscar(egresoRequest.documentoComercial.tipo);
             if(proveedor == null || medioDePago == null || tipoDocumento == null) {
                return null;
             }
        }
        catch (NoResultException ex){
            return null;
        }
        Pago pago = new Pago();
        pago.setMedioDePago(medioDePago);
        pago.setCodigoAsociado(egresoRequest.medioDePago.dato);

        DocumentoComercial documentoComercial = new DocumentoComercial();
        documentoComercial.setTipo(tipoDocumento);
        documentoComercial.setNumDocumento(egresoRequest.documentoComercial.numeroDocumento);
        documentoComercial.setFechaDePedido(egresoRequest.documentoComercial.fechaDePedido);
        documentoComercial.setFechaDeEntrega(egresoRequest.documentoComercial.fechaDeEntrega);
        documentoComercial.setDescripcion(egresoRequest.documentoComercial.descripcion);

        Repositorio<Pago> repoPagos = new Repositorio<>(new DaoHibernate<>(Pago.class));
        Repositorio<DocumentoComercial> repoDocumentos = new Repositorio<>(new DaoHibernate<>(DocumentoComercial.class));

        repoPagos.agregar(pago);
        repoDocumentos.agregar(documentoComercial);

        List<ItemEgreso> items = egresoRequest.items
                                            .stream()
                                            .map(item->mapearItem(item))
                                            .collect(Collectors.toList());

        Egreso egreso = new BuilderEgresoConcreto()
                    .agregarFechaOperacion(egresoRequest.fechaOperacion)
                    .agregarDatosOrganizacion(entidadJuridica)
                    .agregarProveedor(proveedor)
                    .agregarPago(pago)
                    .agregarDocumentoComercial(documentoComercial)
                    .agregarItems(items)
                    .build();

        Repositorio<Egreso> repoEgresos = new Repositorio<>(new DaoHibernate<>(Egreso.class));

        repoEgresos.agregar(egreso);
            //Modificar items con el egreso, o sea linkearlos
        relacionarItemsConEgreso(items,egreso);

        return egreso;
    }

    private ItemEgreso mapearItem(ItemRequest itemRequest) {
        ItemEgreso itemEgreso = new ItemEgreso();
        itemEgreso.setPrecio(itemRequest.precio);
        itemEgreso.setCantidad(itemRequest.cantidad);

        Repositorio<ItemEgreso> repoItems = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        Repositorio<Producto> repoProductos = new Repositorio<>(new DaoHibernate<>(Producto.class));

        Producto producto = buscarProducto(itemRequest.nombreProducto.toLowerCase());
        if (producto == null) {
            producto = new Producto();
            producto.setNombreProducto(itemRequest.nombreProducto);

            repoProductos.agregar(producto);
        }
        itemEgreso.setProducto(producto);

        repoItems.agregar(itemEgreso);

        return  itemEgreso;
    }

    private Producto buscarProducto(String nombreProducto) {
        try {
            Producto producto= (Producto) EntityManagerHelper
                        .createQuery("from Producto where nombre_producto = :nombre")
                        .setParameter("nombre",nombreProducto)
                        .getSingleResult();
            return producto;
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    private void relacionarItemsConEgreso(List<ItemEgreso> items, Egreso egreso) {
        Repositorio<ItemEgreso> repoItems = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        items.forEach(unItem -> {
                unItem.setEgresoAsociado(egreso);
                repoItems.modificar(unItem);
        });
    }

    private EgresoResponse mapearEgreso(Egreso egreso) {
        EgresoResponse egresoResponse = new EgresoResponse();
        egresoResponse.id             = egreso.getId();
        egresoResponse.validado       = egreso.isValidado();
        egresoResponse.montoTotal = egreso.getValorTotal();
        egresoResponse.fechaOperacion = egreso.getFechaOperacion();
        return egresoResponse;
    }

    private boolean verificarSuscripcion(Estandar estandar, Egreso egreso) {
        if(egreso.getRevisores().isEmpty()){
            return false;
        }
        return egreso.getRevisores()
                        .stream()
                        .anyMatch(unRevisor->unRevisor.getId() == estandar.getId());
    }
 
    private class CargaEgresoResponse {
        public int code;
        public String message;
        public int id;
    }

    private class ListadoEgresos {
        public int code;
        public String message;
        public List<EgresoResponse> egresos;
    }

    private class EgresoDetalladoResponse {
        @Expose
        public int code;
        @Expose
        public String message;
        @Expose
        public Egreso egreso;
        @Expose
        public boolean estaSuscrito;
    }
}
