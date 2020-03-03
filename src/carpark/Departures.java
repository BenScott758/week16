package carpark;

/**
 * Models a stream of cars leaving a car park.
 *
 * @author Hugh Osborne
 * @version February 2020
 */
public class Departures extends Thread {

	/**
	 * Stops (non-existent) cars leaving an empty car park.
	 */
	CarParkControl control;

	/**
	 * @param control the car park controller the car park the cars are leaving.
	 */
	public Departures(CarParkControl control) {
		this.control = control;
	}

	/**
	 * A continuous stream of cars leaving the car park.
	 * The car park control ensures that a car cannot leave an empty car park.
	 */
	public void run() {
		try {
			while (true) {
				control.leave();
			}
		} catch (InterruptedException e) {}
	}
}
