package sk.tuke.kpi.oop.game;

public interface Switchable {
    boolean isTurnOn = false;

    void turnOn();
    void turnOff();
    boolean isOn();
}
