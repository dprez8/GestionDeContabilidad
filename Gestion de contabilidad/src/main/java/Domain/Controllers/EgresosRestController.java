package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.ConfigSchedulerRequest;
import Domain.Controllers.DTO.EgresoRequest;
import Domain.Controllers.DTO.EgresoResponse;
import Domain.Controllers.DTO.ItemRequest;
import Domain.Controllers.DTO.ModificarEgresoRequest;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Organizacion.EntidadBase;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Estandar;
import Domain.Exceptions.ExcepcionCreacionEgreso;
import Domain.Entities.ValidadorTransparencia.ValidarConPresupuesto;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import Domain.Controllers.Utils.FormFileManager;

/*****************************************************************/
public class EgresosRestController extends GenericController {
    private Repositorio<Egreso> repoEgresos;

    public EgresosRestController(TokenService tokenService,String tokenPrefix){
        super(tokenService,tokenPrefix);
        this.repoEgresos         = new Repositorio<>(new DaoHibernate<>(Egreso.class));
    }

    public String cargarNuevoEgreso(Request request, Response response) {
        String jsonResponse;
        EgresoRequest egresoRequest = null;
        		this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        try {
        	egresoRequest = this.gson.fromJson(request.body(),EgresoRequest.class);
            }
            catch(Exception ex){
            	 this.respuesta.setCode(404);
                 this.respuesta.setMessage("No se logro mapear el json con el egreso");
                 this.jsonResponse = gson.toJson(this.respuesta);
                 response.body(this.jsonResponse);
                 return response.body();
            }


        Egreso egreso = asignarEgresoDesde(request, egresoRequest);

        if(egreso == null) {
            return error(response, "Problema al cargar el egreso");
        }
        this.respuesta.setCode(200);
        this.respuesta.setMessage("Egreso cargado exitosamente");

        CargaEgresoResponse cargaEgresoResponse = new CargaEgresoResponse();

        cargaEgresoResponse.code    = this.respuesta.getCode();
        cargaEgresoResponse.message = this.respuesta.getMessage();
        cargaEgresoResponse.id      = egreso.getId();
        jsonResponse = this.gson.toJson(cargaEgresoResponse);
        response.body(jsonResponse);

        return response.body();
    }

    public String modificarEgreso(Request request, Response response) {
        String jsonResponse;
        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        ModificarEgresoRequest egresoRequest = null;
        try {
            egresoRequest = this.gson.fromJson(request.body(), ModificarEgresoRequest.class);
        } catch (Exception exception) {
            return error(response, "Error en el tipo de datos al modificar egreso");
        }

        Egreso egreso = this.repoEgresos.buscar(egresoRequest.egreso);

        if(egreso == null) {
            return error(response, "No se encontro el egreso");
        }

        try {
            egreso.getDocumento().setPathAdjunto(egresoRequest.pathAdjuntoDocumentoComercial);
            this.repoEgresos.modificar(egreso);
        } catch (Exception exception) {
            return error(response, "Error al cambiar el path adjunto");
        }

        return respuesta(response, 200,"Egreso modificado exitosamente");
    }

    public String listadoDeEgresos(Request request, Response response) {

        Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);

        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        Organizacion organizacion = usuario.getMiOrganizacion();
        List<EgresoResponse> egresosAEnviar;
        String jsonResponse;

        List<Egreso> egresosBD = organizacion.getEgresos();
        egresosAEnviar = egresosBD
                    .stream()
                    .map(this::mapearEgreso)
                    .collect(Collectors.toList());

        if (egresosAEnviar.isEmpty()) {
            jsonResponse = this.gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return respuesta(response,404,"No hay egresos cargados");
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
        Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
        if(isAdmin(usuario))
            return respuesta(response,403,"No posees permisos de estandar");
        String jsonResponse;
        Egreso egreso;

        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .serializeNulls()
                .create();

        egreso = this.repoEgresos.buscar(new Integer(request.params("egresoId")));

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
        egresoDetallado.presupuesto  = null;

        Presupuesto presupuestoValidado = egreso.getPresupuestoValidado();
        if (presupuestoValidado != null) {
            egresoDetallado.presupuesto = presupuestoValidado.getId();
        }

        //ValidarConPresupuesto validador = new ValidarConPresupuesto();
        //validador.validarEgreso(egreso);
        //if (validador.getPresupuestoElegido() != null) {
        //    egresoDetallado.presupuesto = new Integer(validador.getPresupuestoElegido().getId());
        //}


        jsonResponse = this.gson.toJson(egresoDetallado);
        response.body(jsonResponse);

        return jsonResponse;
    }

    public String suscribirse(Request request, Response response) {
        this.gson = new Gson();
        JsonObject jsonRequest = gson.fromJson(request.body(), JsonObject.class);
        Estandar estandar = (Estandar) getUsuarioDesdeRequest(request);
        try {
            Egreso egreso = this.repoEgresos.buscar(jsonRequest.get("egresoId").getAsInt());
            if(verificarSuscripcion(estandar,egreso))
                return error(response,"El usuario ya esta suscrito al egreso");
            egreso.addRevisores(estandar);
            this.repoEgresos.modificar(egreso);
            return respuesta(response,200,"Suscripcion exitosa");
        }
        catch (Exception ex) {
            return error(response,ex.getMessage());
        }
    }

    /***************Private methods***************************************/
    private Egreso asignarEgresoDesde (Request request, EgresoRequest egresoRequest) {
        Proveedor proveedor;
        MedioDePago medioDePago;
        TipoDocumento tipoDocumento;

        Repositorio<Proveedor> repoProveedores   = new Repositorio<>(new DaoHibernate<>(Proveedor.class));
        Repositorio<TipoDocumento> repoTipoDocumento = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
        Repositorio<MedioDePago> repoMediosDePagos = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));
        Repositorio<Egreso> repoEgresos = new Repositorio<>(new DaoHibernate<>(Egreso.class));

        try {
             proveedor     = repoProveedores.buscar(egresoRequest.proveedor);
             medioDePago   = repoMediosDePagos.buscar(egresoRequest.medioDePago.id);
             tipoDocumento = repoTipoDocumento.buscar(egresoRequest.documentoComercial.tipo);
             if(proveedor == null || medioDePago == null || tipoDocumento == null) {
                return null;
             }
        }
        catch (Exception ex){
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
        documentoComercial.setPathAdjunto(egresoRequest.documentoComercial.nombreFicheroDocumento);

        Estandar usuario = (Estandar) getUsuarioDesdeRequest(request);
        Organizacion organizacion = usuario.getMiOrganizacion();

        List<ItemEgreso> items = egresoRequest.items
                .stream()
                .map(item->mapearItem(item, organizacion))
                .collect(Collectors.toList());
        Egreso egreso;
        try {
             egreso = new BuilderEgresoConcreto()
                    .agregarFechaOperacion(egresoRequest.fechaOperacion)
                    .agregarCantidadPresupuestos(egresoRequest.cantidadPresupuestos)
                    .agregarDatosOrganizacion(organizacion)
                    .agregarProveedor(proveedor)
                    .agregarPago(pago)
                    .agregarDocumentoComercial(documentoComercial)
                    .agregarItems(items)
                    .build();
        }catch (ExcepcionCreacionEgreso ex) {
            return null;
        }


        repoEgresos.agregar(egreso);
        
        //Modificar items con el egreso, o sea linkearlos
        relacionarItemsConEgreso(items,egreso);

        return egreso;
    }

    private ItemEgreso mapearItem(ItemRequest itemRequest, Organizacion organizacion) {
        ItemEgreso itemEgreso = new ItemEgreso();
        Item item=null;
        itemEgreso.setPrecio(itemRequest.precio);
        itemEgreso.setCantidad(itemRequest.cantidad);

        Repositorio<Item> repoItem = new Repositorio<>(new DaoHibernate<>(Item.class));
        Repositorio<TipoItem> repoTipoItem = new Repositorio<>(new DaoHibernate<>(TipoItem.class));

        List<Item> items = repoItem.buscarTodos();
        // filtro los items por organizacion
        try {
            items = items.stream().filter(unItem -> unItem.getOrganizacion().equals(organizacion)).collect(Collectors.toList());
        } catch (Exception exception) {
            System.out.println("oof falle al obtener items");
        }
        // filtro por descripcion
        try {
            item = items.stream()
                    .filter(unItem -> unItem.getDescripcion().equals(itemRequest.descripcion))
                    .findFirst().get();
        } catch (Exception exception) {
            System.out.println("oof falle al obtener un item");
        }

        if (item == null) {
            TipoItem tipoItem = repoTipoItem.buscar(itemRequest.tipoItem);
            item = new Item(itemRequest.descripcion, tipoItem, organizacion);
            repoItem.agregar(item);
        }
       
        itemEgreso.setItem(item);

        return  itemEgreso;
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

    /*********************DTOs(Inner Class)******************************/
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
        @Expose
        public Integer presupuesto;
    }
}
