package pl.tmaj;

public class AdvancedRemote extends Remote {

    public AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        super.device.setVolume(0);
    }
}
