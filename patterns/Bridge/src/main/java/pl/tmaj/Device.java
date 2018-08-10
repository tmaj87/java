package pl.tmaj;

public interface Device {

    boolean isEnabled();

    void disable();

    void enable();

    void setVolume(int volume);

    int getVolume();
}
