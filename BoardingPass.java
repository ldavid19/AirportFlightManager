import java.io.Serializable;

public class BoardingPass implements Serializable {
    private Passenger passenger;
    private Airline airline;
    private Gate gate;

    public BoardingPass(Passenger passenger, Airline airline, Gate gate) {
        this.passenger = passenger;
        this.airline = airline;
        this.gate = gate;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Airline getAirline() {
        return airline;
    }

    public Gate getGage() {
        return gate;
    }

    @Override
    public String toString() {
        return String.format("----------------------------------------\n" +
                "BOARDING PASS FOR FLIGHT 180000 WITH %s\n" +
                "PASSENGER FIRST NAME: %s\n" +
                "PASSENGER LAST NAME: %s\n" +
                "PASSENGER AGE: %d\n" +
                "You can board at gate %s\n" +
                "----------------------------------------", airline.getAirlineName(), passenger.getFirstName(), passenger.getLastName(), passenger.getAge(), gate.getGate());
    }
}
