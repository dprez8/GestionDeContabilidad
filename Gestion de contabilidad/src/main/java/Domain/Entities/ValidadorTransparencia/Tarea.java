package Domain.Entities.ValidadorTransparencia;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Organizacion.*;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.ElementCollection;

public class Scheduler extends TimerTask {
    private Organizacion organizacion;
    private ValidadorDeTransparencia validador;
    private Repositorio<Organizacion> repoOrganizacion;
    private int horaInicio;
    private int minutoInicio;
    private List<Integer> dias;

    public Scheduler(){
        this.repoOrganizacion = new Repositorio<>(new DaoHibernate<>(Organizacion.class));
        this.dias = new ArrayList<>();
    }

    @Override
    public void run() {
        if(retornarDiaActualSegun(this.dias) != 0) {
            List<Egreso> egresos = this.organizacion.getEgresos().stream().filter(a -> a.isValidado() == false).collect(Collectors.toList()); //Lo egresos que no han sido validados o no pasaron las pruebas anteriormente
            egresos.forEach(egreso -> this.validador.validarEgreso(egreso)); //valida cada uno de los egresos que no se habian validado
            this.repoOrganizacion.modificar(this.organizacion);
            System.out.println("valide");
        }
        System.out.println("ejecutando");
    }

    public Calendar delay() {
        Calendar calendar = Calendar.getInstance();
        if(retornarDiaActualSegun(this.dias) == 0) {
            calendar.set(Calendar.HOUR_OF_DAY, this.horaInicio);
            calendar.set(Calendar.MINUTE, this.minutoInicio);
            calendar.set(Calendar.SECOND, 0);
            return calendar;
        }
        calendar.set(Calendar.DAY_OF_WEEK, retornarDiaActualSegun(this.dias));
        calendar.set(Calendar.HOUR_OF_DAY, this.horaInicio);
        calendar.set(Calendar.MINUTE, this.minutoInicio);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    private int retornarDiaActualSegun(List<Integer> dias) {
        Integer dia = dias.stream()
                .filter(unDia->
                        DayOfWeek.of(unDia) == LocalDate.now().getDayOfWeek())
                .findAny()
                .orElse(null);
        if(dia == null)
            return 0;
        switch(dia) {
            case 0:
                return Calendar.SUNDAY;
            case 1:
                return Calendar.MONDAY;
            case 2:
                return Calendar.TUESDAY;
            case 3:
                return Calendar.WEDNESDAY;
            case 4:
                return Calendar.THURSDAY;
            case 5:
                return Calendar.FRIDAY;
            case 6:
                return Calendar.SATURDAY;
            default:
                return 0;
        }
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getMinutoInicio() {
        return minutoInicio;
    }

    public void setMinutoInicio(int minutoInicio) {
        this.minutoInicio = minutoInicio;
    }

    public List<Integer> getDias() {
        return dias;
    }

    public void setDias(List<Integer> dias) {
        this.dias = dias;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public ValidadorDeTransparencia getValidador() {
        return validador;
    }

    public void setValidador(ValidadorDeTransparencia validador) {
        this.validador = validador;
    }
}