package pl.tmaj;

import pl.tmaj.helper.Helper;

import java.util.List;

public class WindowsHelper {

    private final List<Helper> helpers;

    public WindowsHelper(List<Helper> helpers) {
       this.helpers = helpers;
    }

    public void init() throws InterruptedException { // TODO: spawn new thread
        while (true) {
            helpers.forEach(Helper::check);
            Thread.sleep(100);
        }
    }
}
