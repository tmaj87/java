package pl.tmaj;

public class GrabStocks {
    public static void main(String[] args) {
        StockGrabber stockGrabber = new StockGrabber();

        StockObserver observer1 = new StockObserver(stockGrabber);

        exampleUpdates(stockGrabber);

        StockObserver observer2 = new StockObserver(stockGrabber);

        exampleUpdates(stockGrabber);

        stockGrabber.unregister(observer1);

        exampleUpdates(stockGrabber);
    }

    private static void exampleUpdates(StockGrabber stockGrabber) {
        stockGrabber.setIBMPrice(197.);
        stockGrabber.setAAPLPrice(667.6);
        stockGrabber.setGOOGPrice(676.4);
    }
}
