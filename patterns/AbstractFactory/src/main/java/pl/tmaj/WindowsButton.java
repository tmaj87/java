package pl.tmaj;

public class WindowsButton implements Button {

    @Override
    public void paint() {
        System.out.println(getClass());
    }
}
