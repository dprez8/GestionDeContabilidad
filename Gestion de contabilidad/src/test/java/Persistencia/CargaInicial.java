package Persistencia;

import Domain.Entities.CategorizadorDeEmpresas.CategoriaEmpresa;
import Domain.Entities.CategorizadorDeEmpresas.CategoriaPorSector;
import Domain.Entities.CategorizadorDeEmpresas.Sector;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CargaInicial {

    private Repositorio<MedioDePago> repoMedioDePagos;
    private Repositorio<TipoDocumento> repoTipoDocumento;
    private Repositorio<Sector> repoSector;
    private Repositorio<CategoriaEmpresa> repoCategoria;
    private Repositorio<CategoriaPorSector> repoCategoriaSector;

    @Before
    public void antesDePersistir() {
        this.repoMedioDePagos    = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));
        this.repoTipoDocumento   = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
        this.repoSector  		 = new Repositorio<>(new DaoHibernate<>(Sector.class));
        this.repoCategoria   	 = new Repositorio<>(new DaoHibernate<>(CategoriaEmpresa.class));
        this.repoCategoriaSector = new Repositorio<>(new DaoHibernate<>(CategoriaPorSector.class));
        
    }

    @Test
    public void T1persistirTipoDocumentos() {
        TipoDocumento FacturaA = new TipoDocumento("Factura A");
        TipoDocumento FacturaB = new TipoDocumento("Factura B");
        TipoDocumento FacturaC = new TipoDocumento("Factura C");
        this.repoTipoDocumento.agregar(FacturaA);
        this.repoTipoDocumento.agregar(FacturaB);
        this.repoTipoDocumento.agregar(FacturaC);
    }

    @Test
    public void T2persistirMediosDePago() {
        MedioDePago efectivo          = new MedioDePago("Efectivo");
        MedioDePago cheque            = new MedioDePago("Cheque");
        MedioDePago tarjeta_credito   = new MedioDePago("Tarjeta de credito");
        MedioDePago tarjeta_debito    = new MedioDePago("Tarjeta de debito");
        MedioDePago ticket            = new MedioDePago("Ticket");
        MedioDePago atm               = new MedioDePago("ATM");

        this.repoMedioDePagos.agregar(efectivo);
        this.repoMedioDePagos.agregar(cheque);
        this.repoMedioDePagos.agregar(tarjeta_credito);
        this.repoMedioDePagos.agregar(tarjeta_debito);
        this.repoMedioDePagos.agregar(ticket);
        this.repoMedioDePagos.agregar(atm);

    }
 
    @Test
    public void T3persistirSectores() {
    	
    	
    	Sector construccion = new Sector("Construccion");
    	Sector servicios = new Sector("Servicios");
    	Sector comercio = new Sector("Comercio");
    	Sector industria = new Sector("Industria");
    	Sector agropecuaria = new Sector("Agropecuaria");
    	
    	
    	 this.repoSector.agregar(construccion);
         this.repoSector.agregar(industria);
         this.repoSector.agregar(servicios);
         this.repoSector.agregar(agropecuaria);
         this.repoSector.agregar(comercio);
    	 	
    	  CategoriaEmpresa micro   = new CategoriaEmpresa("Micro");
          CategoriaEmpresa pequenia  = new CategoriaEmpresa("Pequenia");
          CategoriaEmpresa medianaT1= new CategoriaEmpresa("Mediana T1");
          CategoriaEmpresa medianaT2 = new CategoriaEmpresa("Mediana T2");
          
          this.repoCategoria.agregar(micro);
          this.repoCategoria.agregar(pequenia);
          this.repoCategoria.agregar(medianaT1);
          this.repoCategoria.agregar(medianaT2);

          CategoriaPorSector microConstruccion     = new CategoriaPorSector(0.0,	19450000.0,1,12);
          CategoriaPorSector pequeniaConstruccion  = new CategoriaPorSector(19450000.0,115370000.0,12,45);
          CategoriaPorSector medianaT1Construccion = new CategoriaPorSector(115370000.0,643710000.0,45,200);
          CategoriaPorSector medianaT2Construccion = new CategoriaPorSector(643710000.0,965460000.0,200,590);
          
          CategoriaPorSector microServicios     = new CategoriaPorSector(0.0, 9900000.0,1,7);
          CategoriaPorSector pequeniaServicios  = new CategoriaPorSector(9900000.0,59710000.0,7,30);
          CategoriaPorSector medianaT1Servicios = new CategoriaPorSector(59710000.0,494200000.0,30,165);
          CategoriaPorSector medianaT2Servicios = new CategoriaPorSector(494200000.0,705790000.0,165,535);
          
          CategoriaPorSector microComercio     = new CategoriaPorSector(0.0, 36320000.0,1,7);
          CategoriaPorSector pequeniaComercio  = new CategoriaPorSector(36320000.0,247200000.0,7,35);
          CategoriaPorSector medianaT1Comercio = new CategoriaPorSector(247200000.0,1821760000.0,35,125);
          CategoriaPorSector medianaT2Comercio = new CategoriaPorSector(1821760000.0,2602540000.0,125,345);
          
          CategoriaPorSector microIndustria     = new CategoriaPorSector(0.0, 33920000.0,1,15);
          CategoriaPorSector pequeniaIndustria  = new CategoriaPorSector(33920000.0,243290000.0,15,60);
          CategoriaPorSector medianaT1Industria = new CategoriaPorSector(243290000.0,1651750000.0,60,235);
          CategoriaPorSector medianaT2Industria = new CategoriaPorSector(643710000.0,2540380000.0,235,655);
          
          CategoriaPorSector microAgropecuaria     = new CategoriaPorSector(0.0, 17260000.0,1,5);
          CategoriaPorSector pequeniaAgropecuaria  = new CategoriaPorSector(17260000.0,71960000.0,5,10);
          CategoriaPorSector medianaT1Agropecuaria = new CategoriaPorSector(71960000.0,426720000.0,10,50);
          CategoriaPorSector medianaT2Agropecuaria = new CategoriaPorSector(426720000.0,676810000.0,50,215);
          
          
          micro.addCategoriasPorSector(microConstruccion,microServicios,microComercio,microIndustria,microAgropecuaria);
          pequenia.addCategoriasPorSector(pequeniaConstruccion,pequeniaServicios,pequeniaComercio,pequeniaIndustria,pequeniaAgropecuaria);
          medianaT1.addCategoriasPorSector(medianaT1Construccion,medianaT1Servicios,medianaT1Comercio,medianaT1Industria,medianaT1Agropecuaria );
          medianaT2.addCategoriasPorSector(medianaT2Construccion,medianaT2Servicios,medianaT2Comercio,medianaT2Industria,medianaT2Agropecuaria);
        
          
          construccion.addCategoriasPorSector(microConstruccion,pequeniaConstruccion,medianaT1Construccion,medianaT2Construccion);
          servicios.addCategoriasPorSector(microServicios,pequeniaServicios,medianaT1Servicios,medianaT2Servicios);
          comercio.addCategoriasPorSector(microComercio,pequeniaComercio,medianaT1Comercio,medianaT2Comercio);
          industria.addCategoriasPorSector(microIndustria,pequeniaIndustria,medianaT1Industria,medianaT2Industria);
          agropecuaria.addCategoriasPorSector(microAgropecuaria,pequeniaAgropecuaria,medianaT1Agropecuaria,medianaT2Agropecuaria);
          
          
          
          this.repoCategoriaSector.agregar(microConstruccion);
          this.repoCategoriaSector.agregar(pequeniaConstruccion);
          this.repoCategoriaSector.agregar(medianaT1Construccion);
          this.repoCategoriaSector.agregar(medianaT2Construccion);
          this.repoCategoriaSector.agregar(microServicios);
          this.repoCategoriaSector.agregar(pequeniaServicios);
          this.repoCategoriaSector.agregar(medianaT1Servicios);
          this.repoCategoriaSector.agregar(medianaT2Servicios);
          this.repoCategoriaSector.agregar(microComercio);
          this.repoCategoriaSector.agregar(pequeniaComercio);
          this.repoCategoriaSector.agregar(medianaT1Comercio);
          this.repoCategoriaSector.agregar(medianaT2Comercio);
          this.repoCategoriaSector.agregar(microIndustria);
          this.repoCategoriaSector.agregar(pequeniaIndustria);
          this.repoCategoriaSector.agregar(medianaT1Industria);
          this.repoCategoriaSector.agregar(medianaT2Industria);
          this.repoCategoriaSector.agregar(microAgropecuaria);
          this.repoCategoriaSector.agregar(pequeniaAgropecuaria);
          this.repoCategoriaSector.agregar(medianaT1Agropecuaria);
          this.repoCategoriaSector.agregar(medianaT2Agropecuaria);
          
     
          
    }
}
