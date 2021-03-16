public class Southwest implements Airline {

    private String airlineDescription;
    private String airlineName;
    private String airlineFullName;

    public Southwest() {
        airlineDescription = "America's most favorite airline, and America's most favorite " +
                "low-cost carrier. Rated number one in customer service. We're proud to serve " +
                "customers at the new John Purdue International Airport at Purdue University!";
        airlineName = "SOUTHWEST";
        airlineFullName = "Southwest Airlines";
    }

    @Override
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
