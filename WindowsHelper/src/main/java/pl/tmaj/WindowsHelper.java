package pl.tmaj;

import pl.tmaj.helper.Helper;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class WindowsHelper {

    private static final int THROTTLE = 100;

    private final ExecutorService pool;

    public WindowsHelper(List<Helper> helpers) {
        this.pool = newFixedThreadPool(helpers.size());
        helpers.forEach(this::newThread);
    }

    private void newThread(Helper helper) {
        pool.submit(() -> {
            while (true) {
                try {
                    helper.check();
                    Thread.sleep(THROTTLE);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    break;
                }
            }
        });
    }

    public void kill() {
        pool.shutdownNow();
    }

    public boolean isRunning() {
        return !pool.isShutdown();
    }
}
