package pl.tmaj;

public class MacOsButton implements Button {

    @Override
    public void paint() {
        System.out.println(getClass());
    }
}
