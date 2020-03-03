package carpark;

/**
 * Controls a car park, and two streams of cars, one modelling cars arriving, and the other cars leaving.
 *
 * @author Hugh Osborne
 * @version February 2020
 */
public class CarParkControl {
	/**
	 * The number of spaces currently available.
	 */
	protected int spaces;
	/**
	 * The size of the car park (the number of spaces if the car park is empty).
	 */
	protected int capacity;

	/**
	 * @param n the size of the car park
	 */
	public CarParkControl(int n) {
		capacity = spaces = n;
		System.out.println(this);
	}

	/**
	 * Allow a car to enter the car park.
	 * @throws InterruptedException if wait() does
	 */
	public synchronized void enter() throws InterruptedException {
		if (spaces == 0) {
			wait();
		}
		--spaces;
		System.out.println(this);
		notify();
	}

    /**
     * Allow a car to leave the car park.
     * @throws InterruptedException if wait() does
     */
    public synchronized void leave() throws InterruptedException {
		if (spaces == capacity) {
			wait();
		}
		++spaces;
		System.out.println(this);
		notify();
	}

    /**
     * Report the current state of the car park.
     * @return a String report on the current state of the car park.
     */
	public String toString() {
		int cars = capacity - spaces;
		return "Carpark: " 
			       + cars + (cars == 1 ? " car," : " cars,")
			       + spaces + (spaces == 1 ? " space." : " spaces.");
	}

}
