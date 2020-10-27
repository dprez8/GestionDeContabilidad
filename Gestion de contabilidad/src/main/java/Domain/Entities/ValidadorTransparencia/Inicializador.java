package Domain.Entities.ValidadorTransparencia;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Organizacion.*;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;

public class Inicializador extends TimerTask {
    private Organizacion organizacion;
    private ValidadorDeTransparencia validador;
    private Repositorio<Organizacion> repoOrganizacion;

    public Inicializador(Organizacion unaOrganizacion, ValidadorDeTransparencia validador) {
        this.organizacion = unaOrganizacion;
        this.validador = validador;
        this.repoOrganizacion = new Repositorio<Organizacion>(new DaoHibernate<>(Organizacion.class));
    }

    @Override
    public void run() {
        List<Egreso> egresos = this.organizacion.getEgresos().stream().filter(a -> a.isValidado() == false).collect(Collectors.toList()); //Lo egresos que no han sido validados o no pasaron las pruebas anteriormente
        egresos.forEach(egreso -> this.validador.validarEgreso(egreso)); //valida cada uno de los egresos que no se habian validado
        this.repoOrganizacion.modificar(this.organizacion);
        System.out.println("ejecutando");
    }
}