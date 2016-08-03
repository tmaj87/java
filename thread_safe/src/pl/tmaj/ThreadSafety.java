package pl.tmaj;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

class ThreadSafety {

    private static final AtomicLong id = new AtomicLong();
    private static long threadId;
    private final int NUMBER_OF_THREADS = 64;
    private final ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private final ReentrantLock masterLock = new ReentrantLock();


    public ThreadSafety() {
        id.incrementAndGet();
        spawnThreads();
        killThreads();
    }

    private void killThreads() {
        pool.shutdown();
    }

    public void spawnThreads() {
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            pool.execute(this::spawnSingleThread);
        }
    }

    private synchronized void spawnSingleThread() {
        threadId++;
        String name = Long.toString(threadId);
        Thread.currentThread().setName(name);
        sayHello();
    }

    private void sayHello() {
        System.out.println("Don't mind me, I'm just " + getName());
    }

    private void doTheFlip() {
        // ...
        synchronized (masterLock) {
            // ...
        }
        // ...do ThreadPoolExecutor stuff
    }

    private String getName() {
        return Thread.currentThread().getName();
    }
}
