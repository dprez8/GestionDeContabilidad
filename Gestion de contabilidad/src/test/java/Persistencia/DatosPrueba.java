package Persistencia;

import Domain.Entities.ApiVinculador.ConfiguracionVinculador;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.CategoriaOperacion;
import Domain.Entities.Operaciones.CriterioOperacion;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Organizacion.Empresa;
import Domain.Entities.Organizacion.EntidadBase;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.ValidadorTransparencia.*;
import Domain.Exceptions.ExcepcionCreacionEgreso;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import db.EntityManagerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatosPrueba {

    private Repositorio<EntidadJuridica> repoEntidadJuridica;
    private Repositorio<EntidadBase> repoEntidadBase;
    private Repositorio<Organizacion> repoOrganizacion;
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Egreso> repoEgresos;
    private Repositorio<ItemEgreso> repoItems;
    private Repositorio<ItemPresupuesto> repoItemPresupuesto;
    private Repositorio<Item> repoItem;
    private Repositorio<TipoItem> repoTipoItem;
    private Repositorio<Pago> repoPagos;
    private Repositorio<MedioDePago> repoMedioDePagos;
    private Repositorio<Proveedor> repoProveedores;
    private Repositorio<DocumentoComercial> repoDocumentos;
    private Repositorio<TipoDocumento> repoTipoDocumento;
    private Repositorio<Presupuesto> repoPresupuestos;
    private Repositorio<Ingreso> repoIngresos;
    private Repositorio<CriterioOperacion> repoCriterios;
    private Repositorio<CategoriaOperacion> repoCategorias;
    private Repositorio<ValidadorDeTransparencia> repoValidadores;
    private Repositorio<ValidacionDeTransparencia> repoValidaciones;
    private Repositorio<SchedulerInit> repoScheduler;

    @Before
    public void antesDePersistir() {
        this.repoEntidadJuridica = new Repositorio<>(new DaoHibernate<>(EntidadJuridica.class));
        this.repoEntidadBase = new Repositorio<>(new DaoHibernate<>(EntidadBase.class));
        this.repoOrganizacion = new Repositorio<>(new DaoHibernate<>(Organizacion.class));
        this.repoUsuarios = new Repositorio<>(new DaoHibernate<>(Usuario.class));
        this.repoEgresos = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.repoItems = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        this.repoItem = new Repositorio<>(new DaoHibernate<>(Item.class));
        this.repoTipoItem = new Repositorio<>(new DaoHibernate<>(TipoItem.class));
        this.repoPagos = new Repositorio<>(new DaoHibernate<>(Pago.class));
        this.repoMedioDePagos = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));
        this.repoItemPresupuesto = new Repositorio<>(new DaoHibernate<>(ItemPresupuesto.class));
        this.repoProveedores = new Repositorio<>(new DaoHibernate<>(Proveedor.class));
        this.repoDocumentos = new Repositorio<>(new DaoHibernate<>(DocumentoComercial.class));
        this.repoTipoDocumento = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
        this.repoPresupuestos = new Repositorio<>(new DaoHibernate<>(Presupuesto.class));
        this.repoIngresos = new Repositorio<>(new DaoHibernate<>(Ingreso.class));
        this.repoCriterios = new Repositorio<>(new DaoHibernate<>(CriterioOperacion.class));
        this.repoCategorias = new Repositorio<>(new DaoHibernate<>(CategoriaOperacion.class));
        this.repoValidadores = new Repositorio<>(new DaoHibernate<>(ValidadorDeTransparencia.class));
        this.repoValidaciones = new Repositorio<>(new DaoHibernate<>(ValidacionDeTransparencia.class));
        this.repoScheduler = new Repositorio<>(new DaoHibernate<>(SchedulerInit.class));
    }

    @Test
    public void T1DatosDePrueba (){
        EntityManagerHelper.getEntityManager().createQuery("");
    }
}
