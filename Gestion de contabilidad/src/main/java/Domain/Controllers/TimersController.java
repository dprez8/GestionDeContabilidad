package Domain.Controllers;

import Domain.Entities.ValidadorTransparencia.Tarea;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class TimersController {
    private Map<Integer, Timer> timerHashMap;
    private Map<Integer, Tarea> schedulerHashMap;

    private static TimersController timersController = null;

    private TimersController() {
        this.timerHashMap = new ConcurrentHashMap<>();
        this.schedulerHashMap = new ConcurrentHashMap<>();
    }

    public static TimersController instancia(){
        if(timersController == null){
            timersController = new TimersController();
        }
        return timersController;
    }

    public Map<Integer, Timer> getTimerHashMap() {
        return timerHashMap;
    }

    public void setTimerHashMap(Map<Integer, Timer> timerHashMap) {
        this.timerHashMap = timerHashMap;
    }

    public Map<Integer, Tarea> getSchedulerHashMap() {
        return schedulerHashMap;
    }

    public void setSchedulerHashMap(Map<Integer, Tarea> schedulerHashMap) {
        this.schedulerHashMap = schedulerHashMap;
    }

    public void setScheduler(Integer schedulerId, Tarea tarea) {
        this.schedulerHashMap.put(schedulerId,tarea);
    }

    public void setTimer(Integer schedulerId, Timer timer) {
        this.timerHashMap.put(schedulerId,timer);
    }

    public void removeScheduler(Integer id) {
        this.schedulerHashMap.remove(id);
    }

    public void removeTimer(Integer id) {
        this.timerHashMap.remove(id);
    }

    public Timer getTimer(Integer id) {
        return timerHashMap.get(id);
    }

    public Tarea getTarea(Integer id) {
        return schedulerHashMap.get(id);
    }
}
