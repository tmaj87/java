package pl.tmaj;

public class AbstractFactory {

    final private Button button;
    final private Checkbox checkbox;

    public AbstractFactory(GUIFactory factory) {
        this.button = factory.createButton();
        this.checkbox = factory.createCheckbox();
    }

    public void paint() {
        button.paint();
        checkbox.paint();
    }
}
