import java.io.Serializable;

public class BookingRequest implements Serializable {

    private Passenger passenger;
    private Airline airline;

    public BookingRequest(Passenger passenger, Airline airline) {
        this.passenger = passenger;
        this.airline = airline;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Airline getAirline() {
        return airline;
    }

    public String getAirlineName() {
        return airline.getAirlineName();
    }
}
