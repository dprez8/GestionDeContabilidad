package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.EgresoRequest;
import Domain.Controllers.DTO.EgresoResponse;
import Domain.Controllers.DTO.ItemRequest;
import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Organizacion.EntidadBase;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*****************************************************************/
public class EgresosRestController {
    private Repositorio<EntidadJuridica> repoEntidadJuridica;
    private Repositorio<EntidadBase> repoEntidadBase;
    private List<Egreso> egresosMemo;
    private Respuesta respuesta;
    private Gson gson;

    public EgresosRestController(){
        this.repoEntidadJuridica = new Repositorio<>(new DaoHibernate<>(EntidadJuridica.class));
        this.repoEntidadBase     = new Repositorio<>(new DaoHibernate<>(EntidadBase.class));
        this.egresosMemo         = new ArrayList<>();
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

        EgresoRequest egresoRequest    = this.gson.fromJson(request.body(),EgresoRequest.class);

        Egreso egreso = asignarEgresoDesde(egresoRequest);

        if(egreso == null) {
            this.respuesta.setCode(400);
            this.respuesta.setMessage("Problema al cargar el egreso");
            jsonResponse = this.gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }
        this.respuesta.setCode(200);
        this.respuesta.setMessage("Egreso cargado exitosamente");

        this.egresosMemo.add(egreso);

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

        List<Egreso> egresosBD = entidadJuridica.getEgresos();
        egresosAEnviar = egresosBD
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

        return jsonResponse;
    }

    private Egreso asignarEgresoDesde (EgresoRequest egresoRequest) {
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

        Egreso egreso = null;
        if(egresoRequest.organizacion_id!=0){
        	if(egresoRequest.tipo==0) {
        		EntidadJuridica entidadJuridica=repoEntidadJuridica.buscar(egresoRequest.organizacion_id);

                egreso = new BuilderEgresoConcreto()
                            .agregarFechaOperacion(egresoRequest.fechaOperacion)
                            .agregarDatosOrganizacion(entidadJuridica)
                            .agregarProveedor(proveedor)
                            .agregarPago(pago)
                            .agregarDocumentoComercial(documentoComercial)
                            .agregarItems(items)
                            .build();

        	}
        	else {
        		EntidadBase entidadBase= repoEntidadBase.buscar(egresoRequest.organizacion_id);

                egreso = new BuilderEgresoConcreto()
                            .agregarFechaOperacion(egresoRequest.fechaOperacion)
                            .agregarDatosOrganizacion(entidadBase)
                            .agregarProveedor(proveedor)
                            .agregarPago(pago)
                            .agregarDocumentoComercial(documentoComercial)
                            .agregarItems(items)
                            .build();

        	}
        }
        

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
        Repositorio<Servicio> repoServicios = new Repositorio<>(new DaoHibernate<>(Servicio.class));

        if(itemRequest.tipoItem==1) {
            Producto producto = buscarProducto(itemRequest.nombreTipo.toLowerCase());
	        if (producto == null) {
	            producto = new Producto();
	            producto.setNombre(itemRequest.nombreTipo);
	
	            repoProductos.agregar(producto);
	        
	        } 
	        itemEgreso.setTipo(producto);
        }
        else {
            Servicio servicio = buscarServicio(itemRequest.nombreTipo.toLowerCase());
    	        if (servicio == null) {
    	            servicio = new Servicio();
    	            servicio.setNombre(itemRequest.nombreTipo);
    	
    	            repoServicios.agregar(servicio);
    	        
    	        } 
    	        itemEgreso.setTipo(servicio);
            }
       

        repoItems.agregar(itemEgreso);

        return  itemEgreso;
    }

    private Producto buscarProducto(String nombreProducto) {
        try {
            Producto producto= (Producto) EntityManagerHelper
                        .createQuery("from Producto where nombre = :nombre")
                        .setParameter("nombre",nombreProducto)
                        .getSingleResult();
            return producto;
        }
        catch (NoResultException ex) {
            return null;
        }
    }
   
    private Servicio buscarServicio(String nombreServicio) {
        try {
           Servicio servicio= (Servicio) EntityManagerHelper
                        .createQuery("from Servicio where nombre = :nombre")
                        .setParameter("nombre",nombreServicio)
                        .getSingleResult();
            return servicio;
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
