package pl.tmaj;

public class Remote {

    protected final Device device;

    public Remote(Device device) {
        this.device = device;
    }

    public void togglePower() {
        if (device.isEnabled()) {
            device.disable();
        } else {
            device.enable();
        }
    }

    public void setVolume(int volume) {
        if (volume > 0 && volume < 100) {
            device.setVolume(volume);
        }
    }
}
