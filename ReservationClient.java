import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReservationClient {
    private static String serverName;
    private static int serverPort;
    private static JFrame frame;
    private static JFrame frame2;

    private static Socket socket;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    private static BookingResponse bookingResponse;

    public static void welcomeScreen() throws IOException {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
        frame.setLayout(new BorderLayout());

        BufferedImage plogo = ImageIO.read(new File("PLogo.png"));
        JLabel plogoLabel = new JLabel(new ImageIcon(plogo));

        frame.add(plogoLabel, BorderLayout.CENTER);

        JLabel titleLable = new JLabel("Welcome to this SuperRealistic Booking Simulator");
        frame.add(titleLable, BorderLayout.PAGE_START);

        JPanel bottomButtonBar = new JPanel();
        frame.add(bottomButtonBar, BorderLayout.PAGE_END);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupTabScreen();
            }
        });
        bottomButtonBar.add(exitButton);
        bottomButtonBar.add(continueButton);

        frame.setSize(new Dimension(550, 300));
        frame.setVisible(true);
    }

    public static void setupTabScreen() {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
        BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(boxLayout);

        JLabel promptLabel = new JLabel("Please select an Airline:");
        frame.add(promptLabel);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tabAlaska = new JPanel();
        JPanel tabDelta = new JPanel();
        JPanel tabSouthwest = new JPanel();

        JTextArea jt1 = new JTextArea(new Alaska().getAirlineDescription(),5,40);
        jt1.setLineWrap(true);
        jt1.setWrapStyleWord(true);
        jt1.setEditable(false);
        jt1.setBorder(null);
        jt1.setBackground(null);
        tabAlaska.add(jt1);
        JButton alaskaFlightInfo = new JButton("View Flight Info");
        alaskaFlightInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnWindows2(new Alaska());
            }
        });
        tabAlaska.add(alaskaFlightInfo);

        JButton alaskaBookFlight = new JButton("Book This Flight");
        alaskaBookFlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to book a flight on Alaska?", "Confirm Airline", JOptionPane.YES_NO_OPTION);
                if(choice == 0) {
                    promptPassengerInfo(new Alaska());
                }

            }
        });
        tabAlaska.add(alaskaBookFlight);

        JTextArea jt2 = new JTextArea(new Delta().getAirlineDescription(),5,40);
        jt2.setLineWrap(true);
        jt2.setWrapStyleWord(true);
        jt2.setEditable(false);
        jt2.setBorder(null);
        jt2.setBackground(null);
        tabDelta.add(jt2);
        JButton deltaFlightInfo = new JButton("View Flight Info");
        deltaFlightInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnWindows2(new Delta());
            }
        });
        tabDelta.add(deltaFlightInfo);

        JButton deltaBookFlight = new JButton("Book This Flight");
        deltaBookFlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to book a flight on Delta?", "Confirm Airline", JOptionPane.YES_NO_OPTION);
                if(choice == 0) {
                    promptPassengerInfo(new Delta());
                }
            }
        });
        tabDelta.add(deltaBookFlight);


        JTextArea jt3 = new JTextArea(new Southwest().getAirlineDescription(),5,40);
        jt3.setLineWrap(true);
        jt3.setWrapStyleWord(true);
        jt3.setEditable(false);
        jt3.setBorder(null);
        jt3.setBackground(null);
        tabSouthwest.add(jt3);
        JButton southwestFlightInfo = new JButton("View Flight Info");
        southwestFlightInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnWindows2(new Southwest());
            }
        });
        tabSouthwest.add(southwestFlightInfo);

        JButton southwestBookFlight = new JButton("Book This Flight");
        southwestBookFlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to book a flight on Southwest?", "Confirm Airline", JOptionPane.YES_NO_OPTION);
                if(choice == 0) {
                    promptPassengerInfo(new Southwest());
                }
            }
        });
        tabSouthwest.add(southwestBookFlight);

        tabbedPane.setBounds(50,50,200,200);
        tabbedPane.add("Alaska",tabAlaska);
        tabbedPane.add("Delta", tabDelta);
        tabbedPane.add("Southwest", tabSouthwest);

        frame.add(tabbedPane);
        frame.pack();
        frame.setSize(new Dimension(550, 300));
        frame.setVisible(true);
    }

    public static void spawnWindows2(Airline airline) {
        frame2 = new JFrame("Reservation List");
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        BoxLayout boxLayout = new BoxLayout(frame2.getContentPane(),BoxLayout.Y_AXIS);
        frame2.setLayout(boxLayout);

        ReservationInfo reservationInfo = ReservationClientTest.getReservationInfo(airline, oos, ois);

        frame2.add(new TextArea(reservationInfo.toString()));
        JButton closeButton = new JButton("Close");
        frame2.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame2.dispose();
            }
        });
        frame2.setSize(new Dimension(240, 340));
        frame2.setVisible(true);
    }

    public static void promptPassengerInfo(Airline airline) {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(boxLayout);

        frame.add(new JLabel("Enter First Name"));
        JTextArea firstNameArea = new JTextArea();
        frame.add(firstNameArea);

        frame.add(new JLabel("Enter Last Name"));
        JTextArea lastNameArea = new JTextArea();
        frame.add(lastNameArea);

        frame.add(new JLabel("Enter Age"));
        JTextArea ageArea = new JTextArea();
        frame.add(ageArea);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean validInputs = false;

                if (!firstNameArea.getText().matches(".*\\d.*")) {
                    if (!lastNameArea.getText().matches(".*\\d.*")) {
                        try {
                            int ageValue = Integer.parseInt(ageArea.getText());
                            if (ageValue < 15) {
                                JOptionPane.showMessageDialog(null, "INVALID AGE INPUT! Please input a valid age number (15+)\n" +
                                        "(All airlines at John Purdue International Airport require ticket bookers to be 15 and over.)\n" +
                                        "We are sorry for the inconvenience.", "Please Enter Valid Age", JOptionPane.ERROR_MESSAGE);
                                ageArea.requestFocus();
                            } else {
                                validInputs = true;
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "INVALID AGE INPUT! Please input a valid age number (15+)\n" +
                                    "(All airlines at John Purdue International Airport require ticket bookers to be 15 and over.)\n" +
                                    "We are sorry for the inconvenience.", "Please Enter Valid Age", JOptionPane.ERROR_MESSAGE);
                            ageArea.requestFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "INVALID NAME INPUT!\n" +
                                "Last name should not contain any numbers!", "Invalid Name Input", JOptionPane.ERROR_MESSAGE);
                        lastNameArea.requestFocus();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "INVALID NAME INPUT!\n" +
                            "First name should not contain any numbers!", "Invalid Name Input", JOptionPane.ERROR_MESSAGE);
                }

                if (validInputs == true) {
                    Passenger passenger = new Passenger(firstNameArea.getText(), lastNameArea.getText(), Integer.parseInt(ageArea.getText()));
                    int choice = JOptionPane.showConfirmDialog(frame, String.format("Are these details correct?\nFirst Name: %s\nLast Name: %s\nAge: %d", passenger.getFirstName(), passenger.getLastName(), passenger.getAge()), "Confirm Details", JOptionPane.YES_NO_OPTION);
                    if(choice == 0) {
                        bookingResponse = ReservationClientTest.reserveTicket(airline, passenger, oos, ois);
                        displayFlightInfo(airline);
                    }
                }
            }
        });
        frame.add(confirmButton);

        frame.setVisible(true);
    }

    public static void displayFlightInfo(Airline airline) {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
        BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(boxLayout);

        JPanel topInfo = new JPanel();
        JPanel midArea = new JPanel();
        JPanel lowBoardingPass = new JPanel();

        JTextArea passengerList = new JTextArea(bookingResponse.getReservationInfo().toString());
        passengerList.setEditable(false);
        JTextArea boardingPassArea = new JTextArea(bookingResponse.getBoardingPass().toString());
        boardingPassArea.setEditable(false);

        JButton updateFLightInfo = new JButton("Update Flight Info");
        updateFLightInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passengerList.setText(ReservationClientTest.getReservationInfo(airline, oos, ois).toString());
            }
        });
        midArea.add(passengerList);
        midArea.add(updateFLightInfo);
        lowBoardingPass.add(boardingPassArea);
        frame.add(topInfo);
        frame.add(midArea);
        frame.add(lowBoardingPass);


        frame.setVisible(true);
    }



    public static void main(String[] args) {
        boolean validSocket = false;
        do {
            try {
                serverName = JOptionPane.showInputDialog(null, "Input Server Name:", "", 1);
                serverPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Input Server Port:", "", 1));

                socket = new Socket(serverName, serverPort);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                validSocket = true;
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Server Details; Try again", "Invalid", JOptionPane.ERROR_MESSAGE);
            }
        } while (!validSocket);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame = new JFrame("Reservation Manager");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    welcomeScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
