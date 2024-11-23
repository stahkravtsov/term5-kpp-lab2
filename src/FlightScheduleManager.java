import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

class FlightScheduleManager {
    private final Map<String, Airport> airports;
    private final Map<String, List<Flight>> flights;

    public FlightScheduleManager() {
        airports = new HashMap<>();
        flights = new HashMap<>();
    }

    public void addAirport(Airport airport) {
        airports.put(airport.getCode(), airport);
    }

    public void addFlight(Flight flight) {
        flights.putIfAbsent(flight.getDeparture().getCode(), new ArrayList<>());
        flights.get(flight.getDeparture().getCode()).add(flight);
    }

    public Airport getAirportByCode(String code) {
        return airports.get(code);
    }

    public List<Flight> findOptimalRoute(String start, String end, String criteria) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, Flight> previousFlights = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Set<String> visited = new HashSet<>();

        distances.put(start, 0.0);
        pq.add(start);

        while (!pq.isEmpty()) {
            String current = pq.poll();

            if (!visited.add(current)) continue;

            if (current.equals(end)) break;

            List<Flight> neighbors = flights.getOrDefault(current, new ArrayList<>());

            for (Flight flight : neighbors) {
                String neighbor = flight.getDestination().getCode();
                if (visited.contains(neighbor)) continue;

                double newDistance = distances.get(current) + (criteria.equals("price") ? flight.getPrice() : flight.getDuration());

                if (newDistance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    previousFlights.put(neighbor, flight);
                    pq.add(neighbor);
                }
            }
        }

        List<Flight> route = new ArrayList<>();
        for (Flight flight = previousFlights.get(end); flight != null; flight = previousFlights.get(flight.getDeparture().getCode())) {
            route.add(flight);
        }

        Collections.reverse(route);
        return route;
    }

    public void loadFlightSchedule(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Airport departure = airports.get(parts[0]);
                Airport destination = airports.get(parts[1]);
                Date departureTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(parts[2]);
                double price = Double.parseDouble(parts[3]);
                int duration = Integer.parseInt(parts[4]);
                String flightNumber = parts[5];
                String planeType = parts[6];
                Flight flight = new Flight(flightNumber, departure, destination, departureTime, price, duration, planeType);
                addFlight(flight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFlightSchedule(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (List<Flight> flightList : flights.values()) {
                for (Flight flight : flightList) {
                    bw.write(flight.getDeparture().getCode() + "," +
                            flight.getDestination().getCode() + "," +
                            new SimpleDateFormat("yyyy-MM-dd HH:mm").format(flight.getDepartureTimeUTC()) + "," +
                            flight.getPrice() + "," +
                            flight.getDuration() + "," +
                            flight.getFlightNumber() + "," +
                            flight.getPlaneType());
                    bw.newLine();
                }
            }
        }
    }

    public void addUserFlight() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть код аеропорту відправлення: ");
        String departureCode = scanner.nextLine();
        Airport departure = airports.get(departureCode);

        System.out.print("Введіть код аеропорту прибуття: ");
        String destinationCode = scanner.nextLine();
        Airport destination = airports.get(destinationCode);

        System.out.print("Введіть дату і час відправлення (yyyy-MM-dd HH:mm) UTC: ");
        String departureTimeStr = scanner.nextLine();
        Date departureTimeUTC = null;
        try {
            departureTimeUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(departureTimeStr);
        } catch (Exception e) {
            System.out.println("Невірний формат дати");
            return;
        }

        System.out.print("Введіть ціну квитка: ");
        double price = scanner.nextDouble();

        System.out.print("Введіть тривалість польоту (в хвилинах): ");
        int duration = scanner.nextInt();
        scanner.nextLine();  // Споживання нової лінії

        System.out.print("Введіть номер рейсу: ");
        String flightNumber = scanner.nextLine();

        System.out.print("Введіть тип літака: ");
        String planeType = scanner.nextLine();

        Flight flight = new Flight(flightNumber, departure, destination, departureTimeUTC, price, duration, planeType);
        addFlight(flight);
        System.out.println("Рейс успішно додано!");
    }
}
