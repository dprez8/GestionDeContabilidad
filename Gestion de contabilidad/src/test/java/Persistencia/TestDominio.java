package Persistencia;

import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Organizacion.Empresa;
import Domain.Entities.Organizacion.EntidadBase;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestDominio {

    private Repositorio<EntidadJuridica> repoEntidadJuridica;

    @Before
    public void antesDePersistir() {
        this.repoEntidadJuridica = new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
    }
    @Test
    public void persistirUnaEntidadJuridica(){

        Empresa pyme = new Empresa();
        pyme.setCantidadDePersonal(3);
        pyme.setVentasAnuales(5000000.3);
        pyme.setActividad("Construcciones");

        EntidadJuridica pymeJuridica = new EntidadJuridica();
        pymeJuridica.setCuit(1234);
        pymeJuridica.setAltura(1234);
        pymeJuridica.setRazonSocial("razonSocial");
        pymeJuridica.setTipoEntidadJuridica(pyme);

        this.repoEntidadJuridica.agregar(pymeJuridica);
        System.out.println("Numero"+ pymeJuridica.getId());
    }

    @Test
    public void obtenerUnaEntidadJuridica() {
        EntidadJuridica pymeJuridica = this.repoEntidadJuridica.buscar(3);

        Assert.assertEquals("razonSocial",pymeJuridica.getRazonSocial());

        Empresa pyme = (Empresa) pymeJuridica.getTipoEntidadJuridica();

        Assert.assertEquals("Construcciones",pyme.getActividad());
    }
}
