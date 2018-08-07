package pl.tmaj;

public class StockObserver implements Observer {
    private static int observerIDTracker = 0;

    private double ibmPrice;
    private double aaplPrice;
    private double googPrice;
    private int observerID;
    private Subject stockGrabber;

    public StockObserver(Subject stockGrabber) {
        this.stockGrabber = stockGrabber;
        observerID = ++observerIDTracker;
        System.out.println("New Observer " + observerID);
        stockGrabber.register(this);
    }

    @Override
    public void update(double ibmPrice, double aaplPrice, double googPrice) {
        this.ibmPrice = ibmPrice;
        this.aaplPrice = aaplPrice;
        this.googPrice = googPrice;

        printThePrices();
    }

    private void printThePrices() {
        System.out.println(observerID + "\nIBM: " + ibmPrice + "\nAAPL: " + aaplPrice + "\nGOOG: " + googPrice);
    }
}
