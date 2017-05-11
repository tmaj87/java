package pl.tmaj;

import java.util.ArrayList;
import java.util.List;

public class StockGrabber implements Subject {
    private List<Observer> observers;
    private double ibmPrice;
    private double aaplPrice;
    private double googPrice;

    public StockGrabber() {
        observers = new ArrayList<>();
    }

    public void register(Observer newObserver) {
        observers.add(newObserver);
    }

    public void unregister(Observer deleteObserver) {
        int observerIndex = observers.indexOf(deleteObserver);
        System.out.println("Observer " + (observerIndex + 1) + " deleted");
        observers.remove(observerIndex);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(ibmPrice, aaplPrice, googPrice);
        }
    }

    public void setIBMPrice(double newIbmPrice) {
        this.ibmPrice = newIbmPrice;
        notifyObservers();
    }

    public void setAAPLPrice(double newAAPLPrice) {
        this.aaplPrice = newAAPLPrice;
        notifyObservers();
    }

    public void setGOOGPrice(double newGOOGPrice) {
        this.googPrice = newGOOGPrice;
        notifyObservers();
    }
}
