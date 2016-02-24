package Application;
import java.util.ArrayList;

public class Train {
	public static enum Direction {
		INBOUND, OUTBOUND
	}

	public static final int PASS_PER_CAR = 12;
	private int capacity = 0;
	private int carsInTrain;
	private int distanceToStation;
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private Direction directionOfTravel;
	private TrainRoute.Station nextStation;

	public Train(Direction dir, int numOfCars, TrainRoute.Station startingStation) {
		directionOfTravel = dir;
		carsInTrain = numOfCars;
		capacity = PASS_PER_CAR * carsInTrain;

		// Put train at random station
		nextStation = startingStation;
	}

	public int getNumberOfCars() {
		return carsInTrain;
	}

	public void addCar() {
		carsInTrain++;
		capacity = PASS_PER_CAR * carsInTrain;
	}

	public void update() {
		System.out.println("Train: " + directionOfTravel + "  Cars = " + carsInTrain);
		System.out.println("	Number of open seats: " + (capacity - passengers.size()));
		System.out.println("	Number of passengers: " + passengers.size());
		if (distanceToStation > 0) {
			System.out.println("	Distance to " + nextStation + " = " + distanceToStation);
		} else {
			System.out.println("	Currently at Station: " + nextStation);
		}
		// If not at station keep moving
		if (distanceToStation > 0) {
			distanceToStation--;
		} else {
			// Unload passengers
			for (int i = passengers.size() - 1; i > 0; i--) {
				if (passengers.get(i).getDestination() == nextStation) {
					passengers.remove(i);
				}
			}

			// Load passengers and reset station
			if (directionOfTravel == Direction.INBOUND) {
				// Leave if train is full or no more passengers are to be boarded
				while (passengers.size() < capacity && !nextStation.getStation().inboundQueue.isEmpty()) {
					passengers.add(nextStation.getStation().inboundQueue.dequeue());
				}

				// Set station
				distanceToStation = nextStation.getDistanceToNext();
				nextStation = nextStation.getNextStation();
			} else {
				// Leave if train is full or no more passengers are to be boarded
				while (passengers.size() < capacity && !nextStation.getStation().outboundQueue.isEmpty()) {
					passengers.add(nextStation.getStation().outboundQueue.dequeue());
				}

				// Set station
				distanceToStation = nextStation.getDistanceToPrevious();
				nextStation = nextStation.getPreviousStation();

			}

		}
	}

	public Direction getDirection() {
		return directionOfTravel;
	}
}