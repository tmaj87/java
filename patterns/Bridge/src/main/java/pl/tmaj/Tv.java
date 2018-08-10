package pl.tmaj;

public class Tv implements Device {

    private boolean powerOn = true;
    private int volume = 0;

    public boolean isEnabled() {
        return powerOn;
    }

    public void disable() {
        powerOn = false;
    }

    public void enable() {
        powerOn = true;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }
}
