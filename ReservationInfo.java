import java.io.Serializable;
import java.util.List;

public class ReservationInfo implements Serializable {

    private List<Passenger> passengers;
    private String airlineName;
    private int maxNumSeats;
    private int takenSeats;

    public ReservationInfo(List<Passenger> passengers, String airlineName) {
        this.passengers = passengers;
        this.airlineName = airlineName;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public int getMaxNumSeats() {
        return maxNumSeats;
    }

    public void setMaxNumSeats(int maxNumSeats) {
        this.maxNumSeats = maxNumSeats;
    }

    public int getTakenSeats() {
        return takenSeats;
    }

    public void setTakenSeats(int takenSeats) {
        this.takenSeats = takenSeats;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < passengers.size(); i++) {
            Passenger passenger = passengers.get(i);
            sb.append(passenger.toString() + "\n");
        }
        //sb.append("Maximum number of seats " + maxNumSeats + "\n");
        //sb.append("Num of Taken seats " + takenSeats + "\n");
        return sb.toString();
    }
}
