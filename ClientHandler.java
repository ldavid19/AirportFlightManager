import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private static String fileName = "reservations.txt";

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        System.out.printf("connection received from %s\n", clientSocket);

        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            InputStream is = clientSocket.getInputStream();
            ois = new ObjectInputStream(is);
            OutputStream os = clientSocket.getOutputStream();
            oos = new ObjectOutputStream(os);

            Object theObject = ois.readObject();
            while (true) {
                if (theObject instanceof BookingRequest) {
                /*
                Creates a temporary bookingRequest object with the ois object that was read.
                Then it will create a bookResponse object using the reserveTicket method to help create it.
                After that, the bookResponse object will be written out with the ObjectOutputStream
                 */
                    BookingRequest bookingRequest = (BookingRequest) theObject;
                    BookingResponse bookingResponse = reserveTicket(bookingRequest);
                    oos.writeObject(bookingResponse);
                    System.out.println("Written bookingResponse Object");
                } else if (theObject instanceof Airline) {
                /*
                Creates a temporary Airline object with the ois object.
                Then it will create a reservationInfo object using the readReservationInfo method to help create it
                After that, the reservationInfo object will be written out with the ObjectOutputStream
                 */
                    Airline airline = (Airline) theObject;
                    ReservationInfo reservationInfo = readReservationInfo(airline);
                    oos.writeObject(reservationInfo);
                    System.out.println("Written reservationInfo Object");
                }

                theObject = ois.readObject();
            }
        } catch (Exception e) {
            System.out.println("Bro, the client socket is closed, yo");
        }
        finally {
            try {
                ois.close();
            } catch (Exception e) {
                System.out.println("Can't close ObjectInputStream object! It's all good tho.");
            }
        }

        try {
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("can't close clientSocket");
        }
    }

    public static synchronized BookingResponse reserveTicket(BookingRequest bookingRequest) {
        //First, get the reservationInfo
        Airline airline = bookingRequest.getAirline();
        String airlineName = bookingRequest.getAirlineName();
        Passenger passenger = bookingRequest.getPassenger();
        ReservationInfo reservationInfo = readReservationInfo(airline);

        //Second, make the BookingResponse
        BookingResponse tempBookingResponse;
        if (reservationInfo.getTakenSeats() == reservationInfo.getMaxNumSeats())
            tempBookingResponse = new BookingResponse(null, reservationInfo);
        else {
            String firstName = passenger.getFirstName();
            String lastName = passenger.getLastName();
            String firstLetterOfFirstName = firstName.substring(0, 1).toUpperCase() + ".";
            String lastNameCapitalized = lastName.toUpperCase();
            Passenger tempPassenger = new Passenger(firstLetterOfFirstName, lastNameCapitalized, passenger.getAge());
            reservationInfo.getPassengers().add(tempPassenger);
            reservationInfo.setTakenSeats(reservationInfo.getTakenSeats() + 1);
            BoardingPass boardingPass = new BoardingPass(passenger, airline, new Gate());
            tempBookingResponse = new BookingResponse(boardingPass, reservationInfo);
        }

        //Third, write it to reservation.txt file
        try {
            ReservationInfo alaskaRI;
            ReservationInfo deltaRI;
            ReservationInfo southwestRI;
            if (airline.getAirlineName().equals("ALASKA")) {
                alaskaRI = reservationInfo;
            }
            else {
                alaskaRI = readReservationInfo(new Alaska());
            }
            if (airline.getAirlineName().equals("DELTA")) {
                deltaRI = reservationInfo;
            }
            else {
                deltaRI = readReservationInfo(new Delta());
            }
            if (airline.getAirlineName().equals("SOUTHWEST")) {
                southwestRI = reservationInfo;
            }
            else {
                southwestRI = readReservationInfo(new Southwest());
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            writeReservationInfo(alaskaRI, bw);
            bw.write("\n");
            writeReservationInfo(deltaRI, bw);
            bw.write("\n");
            writeReservationInfo(southwestRI, bw);
            bw.close();
        } catch (IOException e) {
            System.out.println("Can't write it to the text file fool, ya fool!");
        }
        return tempBookingResponse;
    }

    public static void writeReservationInfo(ReservationInfo reservationInfo, BufferedWriter bw)
            throws IOException {
        bw.write(reservationInfo.getAirlineName() + "\n");
        bw.write(reservationInfo.getTakenSeats() + "/" + reservationInfo.getMaxNumSeats() + "\n");
        
        if (reservationInfo.getAirlineName().equals("ALASKA")) {
            bw.write("Alaska passenger list" + "\n");
        } else if (reservationInfo.getAirlineName().equals("DELTA")) {
            bw.write("Delta passenger list" + "\n");
        } else {
            bw.write("Southwest passenger list" + "\n");
        }

        for (Passenger passenger : reservationInfo.getPassengers()) {
            String firstName = passenger.getFirstName();
            String lastName = passenger.getLastName();
            int age = passenger.getAge();
            bw.write(firstName + " " + lastName + ", " + age + "\n");
            if (reservationInfo.getAirlineName().equals("ALASKA")) {
                bw.write("---------------------ALASKA" + "\n");
            } else if (reservationInfo.getAirlineName().equals("DELTA")) {
                bw.write("---------------------DELTA" + "\n");
            } else {
                bw.write("---------------------SOUTHWEST" + "\n");
            }
        }

    }


    public static ReservationInfo readReservationInfo(Airline airline) {
        List<Passenger> passengersForRI = new ArrayList<Passenger>();
        String airlineNameForRI = airline.getAirlineName();
        int maxNumSeatsForRI = 0;
        int takenSeatsForRI = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String request;
            while ((request = br.readLine()) != null) {
                //ReservationInfo object that's being made attains the name of the airline.
                if (request.equals(airline.getAirlineName())) {
                    //reads the takenSeats/maxNumSeats
                    request = br.readLine();
                    String[] seatValues = request.split("/");
                    takenSeatsForRI = Integer.parseInt(seatValues[0]);
                    maxNumSeatsForRI = Integer.parseInt(seatValues[1]);
                } else if (request.trim().length() != 0) {
                    //reads the names of the passengers that have already taken seats on the flight
                    String[] nameAndAge = request.split(", ");
                    if (nameAndAge.length > 1) {
                        String[] firstLetterAndLastName = nameAndAge[0].split(" ");
                        String firstLetter = firstLetterAndLastName[0];
                        String lastName = firstLetterAndLastName[1];
                        int age = Integer.parseInt(nameAndAge[1]);
                        if ((request = br.readLine()).contains(airline.getAirlineName()))
                            passengersForRI.add(new Passenger(firstLetter, lastName, age));
                    }
                }
            }

            ReservationInfo tempRI = new ReservationInfo(passengersForRI, airlineNameForRI);
            tempRI.setMaxNumSeats(maxNumSeatsForRI);
            tempRI.setTakenSeats(takenSeatsForRI);
            br.close();
            return tempRI;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
