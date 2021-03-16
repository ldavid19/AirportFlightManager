import java.io.Serializable;

public class BookingResponse implements Serializable {

    private BoardingPass boardingPass;
    private ReservationInfo reservationInfo;

    public BookingResponse(BoardingPass boardingPass, ReservationInfo reservationInfo) {
        this.boardingPass = boardingPass;
        this.reservationInfo = reservationInfo;
    }

    public BoardingPass getBoardingPass() {
        return boardingPass;
    }

    public ReservationInfo getReservationInfo() {
        return reservationInfo;
    }

    public String toString() {
        if ( boardingPass != null ) {
            return boardingPass.toString() + "\n" + reservationInfo.toString();
        } else {
            return "boardingPass is null, yo";
        }
    }
}
