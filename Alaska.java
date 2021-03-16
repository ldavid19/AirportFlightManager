public class Alaska implements Airline {

    private String airlineDescription;
    private String airlineName;
    private String airlineFullName;

    public Alaska() {
        airlineDescription = "Proudly flying passengers at the new John Purdue International " +
                "Airport at Purdue University. We primarily fly towards west coast locations " +
                "and favorably Alaska. We have world class in-flight hospitality offering " +
                "delicious snacks and in-flight entertainment.";
        airlineName = "ALASKA";
        airlineFullName = "Alaska Airlines";
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
