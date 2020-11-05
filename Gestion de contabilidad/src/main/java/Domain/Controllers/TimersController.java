package Domain.Controllers;

import Domain.Entities.ValidadorTransparencia.Tarea;

import java.util.HashMap;
import java.util.Timer;

public class TimersController {
    private HashMap<Integer, Timer> timerHashMap;
    private HashMap<Integer, Tarea> schedulerHashMap;

    private static TimersController timersController = null;

    private TimersController() {
        this.timerHashMap = new HashMap<Integer, Timer>();
        this.schedulerHashMap = new HashMap<Integer, Tarea>();
    }

    public static TimersController instancia(){
        if(timersController == null){
            timersController = new TimersController();
        }
        return timersController;
    }

    public HashMap<Integer, Timer> getTimerHashMap() {
        return timerHashMap;
    }

    public void setTimerHashMap(HashMap<Integer, Timer> timerHashMap) {
        this.timerHashMap = timerHashMap;
    }

    public HashMap<Integer, Tarea> getSchedulerHashMap() {
        return schedulerHashMap;
    }

    public void setSchedulerHashMap(HashMap<Integer, Tarea> schedulerHashMap) {
        this.schedulerHashMap = schedulerHashMap;
    }

}
