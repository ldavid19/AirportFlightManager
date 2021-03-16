public class Delta implements Airline {

    private String airlineDescription;
    private String airlineName;
    private String airlineFullName;

    public Delta() {
        airlineDescription = "One of the world's largest global airlines with non-stop flights to "
                + "various locations around the world. We are now serving domestic and international "
                + "flights from the new John Purdue International Airport at Purdue University";
        airlineName = "DELTA";
        airlineFullName = "Delta Airlines";
    }

    public String getAirlineDescription() {
        return airlineDescription;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getAirlineFullName() {
        return airlineFullName;
    }



}
