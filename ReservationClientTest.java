import java.io.*;
import java.net.*;

public class ReservationClientTest {

    private static String hostname;
    private static int port;

    /*
    Yeah, this was from the previous Network I/O HW.
     */
    private static boolean isParsable(String string) {
        return string.chars()
                .mapToObj(Character::isDigit)
                .reduce(Boolean::logicalAnd)
                .orElse(Boolean.FALSE);
    }


    /*

    This main method tests out the Client, Server, and Client Handler by adding a new passenger (me, David Li) on an
    Alaska Airlines flight.
    I copy and pasted the code from the previous Network I/O object to help connect the client to the server
    via a Scanner. Multiple toStrings have been added that displays what is happening in the console.

     */
    public static void main(String[] args) {
        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
        String portString;
        Socket socket;
        BufferedWriter socketWriter = null;
        BufferedReader socketReader = null;
        String request;
        String response;

        //Connect to localhost & port # using Scanner
        try {
            System.out.print("Enter the server's hostname: ");

            hostname = userInputReader.readLine();

            if (hostname == null) {
                System.out.println();

                System.out.println("Goodbye!");

                return;
            } //end if

            System.out.println();

            System.out.print("Enter the server's port: ");

            portString = userInputReader.readLine();

            if (portString == null) {
                System.out.println();

                System.out.println("Goodbye!");

                return;
            } else if (!isParsable(portString)) {
                System.out.println();

                System.out.println("Error: the specified port must be a non-negative integer!");

                System.out.println();

                System.out.println("Goodbye!");

                return;
            } else {
                port = Integer.parseInt(portString);
            } //end if

            socket = new Socket(hostname, port);

            socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println();


        } catch (Exception e) {
            e.printStackTrace();
        }


        //The is a test case.
        try {
            // get reservation info
            //
            Airline airline = new Alaska();
            //ReservationInfo reservationInfo = ReservationClientTest.getReservationInfo(airline);
            //System.out.println(reservationInfo.toString());

            // Book a flight
            //
            Passenger passenger = new Passenger("David", "Li", 19);
            //BookingResponse bookingResponse = ReservationClientTest.reserveTicket(airline, passenger);
            //System.out.println(bookingResponse.toString());
        } catch(Exception e) {
            System.out.println("Exception:" + e);
        }


    }

    /*
    Use the getReservationInfo method to get all the information about a certain airline flight.
    You can put all that into a ReservationInfo object.

    To make it easier to debug, I made a toString for the reservationInfo object that looks like this:
    Passenger[A., NARAIN, 20]
    Passenger[K., ABHYANKAR, 19]
    Passenger[N., CLAYMAN, 19]
    Passenger[D., Li, 19]
    Maximum number of seats 100
    Num of Taken seats 4
     */
    public static ReservationInfo getReservationInfo(Airline airline, ObjectOutputStream oos, ObjectInputStream ois) {

        try {
            oos.writeObject(airline);
            return (ReservationInfo) ois.readObject();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    Use the reserveTicket method to create a bookingResponse Object while also adding the inputted Passenger on the
    inputted flight.
     */
    public static BookingResponse reserveTicket(Airline airline, Passenger passenger, ObjectOutputStream oos, ObjectInputStream ois) {
        try {
            BookingRequest bookingRequest = new BookingRequest(passenger, airline);
            oos.writeObject(bookingRequest);
            return (BookingResponse) ois.readObject();

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
