import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class Flight {
    private final String flightNumber;
    private final Airport departure;
    private final Airport destination;
    private final Date departureTimeUTC;
    private final double price;
    private final int duration; // in minutes
    private final String planeType;

    public Flight(String flightNumber, Airport departure, Airport destination, Date departureTimeUTC, double price, int duration, String planeType) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTimeUTC = departureTimeUTC;
        this.price = price;
        this.duration = duration;
        this.planeType = planeType;
    }

    public Airport getDeparture() {
        return departure;
    }

    public Airport getDestination() {
        return destination;
    }

    public Date getDepartureTimeUTC() {
        return departureTimeUTC;
    }

    public String getLocalDepartureTime() {
        return convertToLocalTime(departure.getTimezone(), departureTimeUTC);
    }

    public String getLocalArrivalTime() {
        Date arrivalTime = new Date(departureTimeUTC.getTime() + TimeUnit.MINUTES.toMillis(duration));
        return convertToLocalTime(destination.getTimezone(), arrivalTime);
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    private String convertToLocalTime(ZoneId timezone, Date utcTime) {
        ZonedDateTime utcZonedTime = utcTime.toInstant().atZone(ZoneId.of("UTC"));
        ZonedDateTime localZonedTime = utcZonedTime.withZoneSameInstant(timezone);
        return localZonedTime.toString();
    }

    public String getPlaneType() {
        return planeType;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", departure=" + departure.getName() +
                ", destination=" + destination.getName() +
                ", departureTimeUTC=" + departureTimeUTC +
                ", price=" + price +
                ", duration=" + duration +
                " mins, planeType='" + planeType + '\'' +
                ", localDepartureTime=" + getLocalDepartureTime() +
                ", currentZoneDepartureTime=" + convertToLocalTime(ZoneId.systemDefault(), departureTimeUTC) +
                ", localArrivalTime=" + getLocalArrivalTime() +
                ", currentZoneArrivalTime=" + convertToLocalTime(ZoneId.systemDefault(), new Date(departureTimeUTC.getTime() + TimeUnit.MINUTES.toMillis(duration))) +
                '}';
    }
}