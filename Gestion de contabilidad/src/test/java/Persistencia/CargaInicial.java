package Persistencia;

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

    @Before
    public void antesDePersistir() {
        this.repoMedioDePagos    = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));
        this.repoTipoDocumento   = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
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
}
