package pl.tmaj;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new MultiDimention());
        thread.start();

        //

        Thread secondThread = new Thread("my name is Joe") {
            public void run() {
            }
        };
        secondThread.start();
        System.out.println("hi, " + secondThread.getName());

        //

        for(int i=0; i<32; i++){
            new Thread("" + i){
                public void run(){
                    try {
                        Thread.sleep(new Random().nextInt(10)+1*100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(getName() + " greets");
                }
            }.start();
        }

        // thread pools
    }

    private static class MultiDimention implements Runnable {

        private double id = new Random().nextDouble();

        public void run() {
            System.out.println("hello world");
        }
    }
}
