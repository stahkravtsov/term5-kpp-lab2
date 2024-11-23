import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class FlightAnalyzerApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FlightScheduleManager flightScheduleManager = new FlightScheduleManager();

    public static void main(String[] args) throws IOException {

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                return;
            }

            System.out.println(EmailValidator.swapDomainLevels(input));
        }

  /*      initializeAirports();
        flightScheduleManager.loadFlightSchedule("flights.txt");

        while (true) {
            System.out.println("1. Пошук оптимального маршруту");
            System.out.println("2. Додати новий рейс");
            System.out.println("3. Вихід");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 3) break;

            if (choice == 1) {
                searchForOptimalRoute();
            } else if (choice == 2) {
                flightScheduleManager.addUserFlight();
                flightScheduleManager.saveFlightSchedule("flights.txt");
            }
        }

        EmailValidator validator = new EmailValidator();


        String text = Files.readString(Paths.get("emails.txt"));

        List<String> emails = validator.findValidEmails(text);

        if (!emails.isEmpty()) {
            System.out.println("Valid email addresses found:");
            for (String email : emails) {
                System.out.println(email);
            }
        } else {
            System.out.println("No valid email addresses found.");
        }*/
    }

    private static void initializeAirports() {
        flightScheduleManager.addAirport(new Airport("KBP", "Kyiv Boryspil", "Europe/Kyiv"));
        flightScheduleManager.addAirport(new Airport("JFK", "New York JFK", "America/New_York"));
        flightScheduleManager.addAirport(new Airport("CDG", "Paris Charles de Gaulle", "Europe/Paris"));
        flightScheduleManager.addAirport(new Airport("LHR", "London Heathrow", "Europe/London"));
    }

    private static void searchForOptimalRoute() {
        System.out.print("Введіть код аеропорту відправлення: ");
        String departure = scanner.nextLine();

        System.out.print("Введіть код аеропорту прибуття: ");
        String destination = scanner.nextLine();

        System.out.print("Шукати за (price/time): ");
        String criteria = scanner.nextLine();

        List<Flight> route = flightScheduleManager.findOptimalRoute(departure, destination, criteria);

        if (route.isEmpty()) {
            System.out.println("Маршрути не знайдено.");
            return;
        }

        System.out.println("Оптимальний маршрут:");
        for (Flight flight : route) {
            System.out.println(flight);
        }
    }
}