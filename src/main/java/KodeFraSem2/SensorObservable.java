package KodeFraSem2;

public interface SensorObservable extends Runnable {
    //bliver observeret og registreret af SensorObserver
    public void registerObserver(SensorObserver sensorObserver);
}
