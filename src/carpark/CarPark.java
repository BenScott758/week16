package carpark;

/**
 * Implements a car park, with a stream of cars entering and leaving.
 *
 * @author Hugh Osborne
 * @version february 2020
 */
public class CarPark {

	/**
	 * Set up the car park with a car park controller, a stream of cars entering, and a stream leaving.
	 * @param n the size of the car park.
	 */
	public CarPark(int n) {
		CarParkControl control = new CarParkControl(n);
		Arrivals arrivals = new Arrivals(control);
		Departures departures = new Departures(control);
		arrivals.start();
		departures.start();
		try {
			arrivals.join();
			departures.join();
		} catch (InterruptedException e) {}
	}

	/**
	 * Demonstrate the code using a car park of size 25.
	 * @param args not used.
	 */
	public static void main(String[] args) {
		new CarPark(25);
	}
}
