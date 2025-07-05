package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TrainService {
    private static final String TRAINS_PATH = "app/src/main/java/ticket/booking/localDb/trains.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    // ✅ ADMIN CREDENTIALS
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    // ✅ Admin login method
    public boolean isAdminAuthenticated() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    // ✅ Fetch all trains from JSON file
    public List<Train> getAllTrains() {
        try {
            File trainFile = new File(TRAINS_PATH);
            if (trainFile.exists()) {
                return objectMapper.readValue(trainFile, new TypeReference<List<Train>>() {});
            }
        } catch (IOException e) {
            System.out.println("Error reading train data: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // ✅ Match trains based on source and destination (case-insensitive)
    public List<Train> getMatchingTrains(String userSource, String userDestination) {
        List<Train> matchingTrains = new ArrayList<>();

        try {
            File trainFile = new File(TRAINS_PATH);
            if (!trainFile.exists()) {
                return matchingTrains;
            }

            List<Train> allTrains = objectMapper.readValue(trainFile, new TypeReference<List<Train>>() {});

            for (Train train : allTrains) {
                LinkedHashMap<String, String> stationTimes = (LinkedHashMap<String, String>) train.getStationTimes();

                if (stationTimes == null || stationTimes.isEmpty()) continue;

                List<String> stationList = new ArrayList<>(stationTimes.keySet());

                String sourceFromJson = stationList.get(0);
                String destinationFromJson = stationList.get(stationList.size() - 1);

                if (sourceFromJson.equalsIgnoreCase(userSource) &&
                        destinationFromJson.equalsIgnoreCase(userDestination)) {
                    matchingTrains.add(train);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading train data: " + e.getMessage());
        }

        return matchingTrains;
    }

    public int countAvailableSeats(List<List<Integer>> seatMatrix) {
        int count = 0;
        for (List<Integer> row : seatMatrix) {
            for (Integer seat : row) {
                if (seat == 1) count++;
            }
        }
        return count;
    }

    public boolean bookFirstAvailableSeat(Train train) {
        List<List<Integer>> seats = train.getSeats();
        for (int i = 0; i < seats.size(); i++) {
            for (int j = 0; j < seats.get(i).size(); j++) {
                if (seats.get(i).get(j) == 1) {
                    seats.get(i).set(j, 0); // mark seat as booked
                    return true;
                }
            }
        }
        return false;
    }

    // ✅ Save full train list back to JSON
    public void saveTrains(List<Train> trainList) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(TRAINS_PATH), trainList);
        } catch (IOException e) {
            System.out.println("Error saving train data: " + e.getMessage());
        }
    }

    public void printTrainSummary(List<Train> trains) {
        System.out.println("\nTrain Schedule:");
        for (Train train : trains) {
            System.out.println("Train No: " + train.getTrainNo());
            System.out.println("Arrival Time: " + train.getArrivalTime());
            System.out.println("Departure Time: " + train.getDepartureTime());
            System.out.println("Stations:");
            train.getStationTimes().forEach((station, time) ->
                    System.out.println(" - " + station + " at " + time)
            );
            System.out.println("Available Seats: " + countAvailableSeats(train.getSeats()));
            System.out.println("----------------------------------------------------");
        }
    }

    // ✅ Create train route only if admin is authenticated
    public void createTrainRoute() {
        if (!isAdminAuthenticated()) {
            System.out.println("Access denied. Only admin can create train routes.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Train Number: ");
        String trainNo = scanner.nextLine();

        System.out.print("Enter number of rows in seat matrix: ");
        int rows = scanner.nextInt();

        System.out.print("Enter number of columns in seat matrix: ");
        int cols = scanner.nextInt();
        scanner.nextLine(); // consume newline

        List<List<Integer>> seatMatrix = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(random.nextBoolean() ? 1 : 0);
            }
            seatMatrix.add(row);
        }

        System.out.print("Enter number of stations (including source & destination): ");
        int stationCount = scanner.nextInt();
        scanner.nextLine();

        Map<String, String> stationTimes = new LinkedHashMap<>();
        for (int i = 0; i < stationCount; i++) {
            System.out.print("Enter station name: ");
            String station = scanner.nextLine();
            System.out.print("Enter time (HH:mm): ");
            String time = scanner.nextLine();
            stationTimes.put(station, time);
        }

        System.out.print("Enter departure time (HH:mm): ");
        String departureTime = scanner.nextLine();

        System.out.print("Enter arrival time (HH:mm): ");
        String arrivalTime = scanner.nextLine();

        Train newTrain = new Train(trainNo, seatMatrix, stationTimes, arrivalTime, departureTime);

        // Get all existing trains, append the new train
        List<Train> allTrains = getAllTrains();
        allTrains.add(newTrain);

        // Save back to file
        saveTrains(allTrains);

        System.out.println("Train route added successfully!");
    }

    public int[] bookAndGetSeatIndex(Train train) {
        List<List<Integer>> seats = train.getSeats();
        for (int i = 0; i < seats.size(); i++) {
            for (int j = 0; j < seats.get(i).size(); j++) {
                if (seats.get(i).get(j) == 1) {
                    seats.get(i).set(j, 0); // mark seat as booked
                    return new int[]{i, j}; // return position
                }
            }
        }
        return null;
    }

}
