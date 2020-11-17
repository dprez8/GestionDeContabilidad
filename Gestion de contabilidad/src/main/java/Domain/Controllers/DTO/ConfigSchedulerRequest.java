package Domain.Controllers.DTO;

import java.util.List;

public class ConfigSchedulerRequest {
    public Integer horaInicio;
    public Integer minutoInicio;
    public List<Integer> dias;
    public int organizacionId;
}
