package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;

public class SchedulerTestPersistencia {
    public static void main(String[] args) {
        Repositorio<EntidadJuridica> repoEntidadJuridica = new Repositorio<>(new DaoHibernate<>(EntidadJuridica.class));
        EntidadJuridica pepsi = repoEntidadJuridica.buscar(1);

        Scheduler scheduler = new Scheduler(pepsi.getUsuarios().get(0));

        /**Creacion de los validadores*/
        ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima(1);
        ValidarConPresupuesto validacionPresupuesto = new ValidarConPresupuesto();
        ValidarMenorValor validacionMenorValor = new ValidarMenorValor();

        ValidadorDeTransparencia validador = new ValidadorDeTransparencia(validacionMinima,validacionPresupuesto,validacionMenorValor);

        scheduler.setHoraInicio(19);
        scheduler.setMinutoInicio(40);
        scheduler.arrancarTarea(pepsi,validador);
    }
}