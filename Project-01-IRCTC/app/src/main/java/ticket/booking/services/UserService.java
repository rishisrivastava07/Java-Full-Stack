package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private User user;
    private List<User> userList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";
    private TrainService trainService = new TrainService();

    public UserService() {
        listOfUsers();
    }

    public User getLoggedInUser() {
        return this.user;
    }

    public void listOfUsers() {
        try {
            File usersFile = new File(USERS_PATH);
            if (usersFile.exists() && usersFile.length() > 0) {
                userList = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
            } else {
                userList = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
            userList = new ArrayList<>();
        }
    }

    public void printAllUsers() {
        List<User> userList;

        try {
            File usersFile = new File(USERS_PATH);
            if (usersFile.exists() && usersFile.length() > 0) {
                userList = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
            } else {
                userList = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
            return;
        }

        if (userList.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("\n=========================== Registered Users ===========================");
            System.out.printf("%-12s | %-15s | %-20s | %-25s | %-12s%n",
                    "User ID", "Username", "Full Name", "Email ID", "Phone No");
            System.out.println("-------------------------------------------------------------------------------" +
                    "---------------------------");

            for (User user : userList) {
                System.out.printf("%-12s | %-15s | %-20s | %-25s | %-12s%n",
                        user.getUserId(),
                        user.getUsername(),
                        user.getFullName(),
                        user.getEmailId(),
                        user.getPhoneNo());
            }

            System.out.println("-------------------------------------------------------------------------------" +
                    "---------------------------");
            System.out.println("Total Users: " + userList.size());
        }
    }

    public void saveUsers() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(USERS_PATH), userList);
        } catch (IOException e) {
            System.out.println("Failed to save users: " + e.getMessage());
        }
    }

    public void signUp() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter email: ");
        String emailId = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNo = scanner.nextLine();

        User newUser = new User(username, fullName, password, emailId, phoneNo);
        userList.add(newUser);
        saveUsers();
        this.user = newUser;

        System.out.println("Signup successful. Welcome, " + fullName + "!");
    }

    public void login() {
        listOfUsers();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User existingUser : userList) {
            try {
                if (existingUser.getUsername().equalsIgnoreCase(username)) {
                    boolean passwordMatch = UserServiceUtil.checkPassword(password, existingUser.getHashedPassword());
                    if (passwordMatch) {
                        this.user = existingUser;
                        System.out.println("Login successful. Welcome, " + existingUser.getFullName() + "!");
                        return;
                    } else {
                        System.out.println("Incorrect password.");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("No user found with username: " + username);
    }

    public void listOfBookedTickets(User user) {
        if (user == null) {
            System.out.println("No user is logged in or user is null.");
            return;
        }

        List<Ticket> tickets = user.getTicketsBooked();

        if (tickets == null || tickets.isEmpty()) {
            System.out.println("No tickets booked yet.");
            return;
        }

        System.out.println("---- Booked Tickets for " + user.getFullName() + " ----");
        for (Ticket ticket : tickets) {
            String formattedDate = ticket.getDateOfTravel();
            try {
                formattedDate = java.time.ZonedDateTime.parse(ticket.getDateOfTravel())
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
            } catch (Exception e) {
                // fallback to original if parsing fails
            }

            System.out.println("Ticket ID: " + ticket.getTicketId());
            System.out.println("Source: " + ticket.getSource());
            System.out.println("Destination: " + ticket.getDestination());
            System.out.println("Date of Travel: " + formattedDate);
            System.out.println("Train Number: " + (ticket.getTrain() != null ? ticket.getTrain().getTrainNo() : "N/A"));
            System.out.println("--------------------------------------------------");
        }
    }

    public void bookTicket(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter source station: ");
        String source = scanner.nextLine();

        System.out.print("Enter destination station: ");
        String destination = scanner.nextLine();

        System.out.print("Enter date of travel (ISO format e.g. 2025-07-10T08:00:00Z): ");
        String dateOfTravel = scanner.nextLine();

        List<Train> trainList = trainService.getMatchingTrains(source, destination);
        if (trainList.isEmpty()) {
            System.out.println("No trains available.");
            return;
        }

        System.out.println("\nAvailable Trains:");
        for (int i = 0; i < trainList.size(); i++) {
            Train train = trainList.get(i);
            int available = trainService.countAvailableSeats(train.getSeats());
            System.out.println((i + 1) + ". Train No: " + train.getTrainNo() + " | Available Seats: " + available);
        }

        System.out.print("Choose train number: ");
        int selected = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (selected < 1 || selected > trainList.size()) {
            System.out.println("Invalid train selection.");
            return;
        }

        Train selectedTrain = trainList.get(selected - 1);

        // Find first available seat
        List<List<Integer>> seats = selectedTrain.getSeats();
        boolean booked = false;
        int seatRow = -1;
        int seatCol = -1;

        outer:
        for (int i = 0; i < seats.size(); i++) {
            for (int j = 0; j < seats.get(i).size(); j++) {
                if (seats.get(i).get(j) == 1) {
                    seats.get(i).set(j, 0); // Book seat
                    seatRow = i;
                    seatCol = j;
                    booked = true;
                    break outer;
                }
            }
        }

        if (!booked) {
            System.out.println("No seats available on this train.");
            return;
        }

        // Create ticket and store seat info
        Ticket ticket = new Ticket(source, destination, dateOfTravel, selectedTrain, user.getUserId());
        ticket.setSeatRow(seatRow);
        ticket.setSeatCol(seatCol);

        user.bookTicket(ticket);

        System.out.println("Ticket booked successfully! Ticket ID: " + ticket.getTicketId() +
                " | Seat: [" + seatRow + "," + seatCol + "]");

        saveUsers();
        trainService.saveTrains(trainList);
    }


    public void cancelTicket(User user) {
        if (user == null) {
            System.out.println("No user is logged in or user is null.");
            return;
        }

        List<Ticket> tickets = user.getTicketsBooked();
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("No tickets to cancel.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n---- Your Booked Tickets ----");
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            System.out.println((i + 1) + ". Ticket ID: " + ticket.getTicketId() +
                    " | From: " + ticket.getSource() +
                    " | To: " + ticket.getDestination() +
                    " | Date: " + ticket.getDateOfTravel() +
                    " | Train No: " + (ticket.getTrain() != null ? ticket.getTrain().getTrainNo() : "N/A"));
        }

        System.out.print("\nEnter Ticket ID to cancel: ");
        String ticketIdToCancel = scanner.nextLine();

        Ticket ticketToRemove = null;
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId().equalsIgnoreCase(ticketIdToCancel)) {
                ticketToRemove = ticket;
                break;
            }
        }

        if (ticketToRemove == null) {
            System.out.println("No ticket found with ID: " + ticketIdToCancel);
            return;
        }

        Train bookedTrain = ticketToRemove.getTrain();
        if (bookedTrain != null) {
            List<Train> allTrains = trainService.getAllTrains();

            for (Train train : allTrains) {
                if (train.getTrainNo().equals(bookedTrain.getTrainNo())) {
                    int row = ticketToRemove.getSeatRow();
                    int col = ticketToRemove.getSeatCol();

                    if (row >= 0 && col >= 0 &&
                            row < train.getSeats().size() &&
                            col < train.getSeats().get(row).size()) {

                        train.getSeats().get(row).set(col, 1);  // Restore seat
                        System.out.println("Seat [" + row + "," + col + "] restored in Train No: " + train.getTrainNo());
                    } else {
                        System.out.println("Invalid seat coordinates in ticket.");
                    }
                    break;
                }
            }

            trainService.saveTrains(allTrains);  // Save updated trains
            System.out.println("Updated train seat matrix saved to trains.json.");
        }

        user.getTicketsBooked().remove(ticketToRemove);  // Remove from user
        saveUsers();  // Save updated user list
        System.out.println("Ticket canceled successfully and changes saved.");
    }




    private boolean restoreFirstBookedSeat(List<List<Integer>> seats) {
        for (int i = 0; i < seats.size(); i++) {
            for (int j = 0; j < seats.get(i).size(); j++) {
                if (seats.get(i).get(j) == 0) {
                    seats.get(i).set(j, 1); // restore seat
                    return true;
                }
            }
        }
        return false;
    }
}