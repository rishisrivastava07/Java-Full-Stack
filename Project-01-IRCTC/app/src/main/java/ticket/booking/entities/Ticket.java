package ticket.booking.entities;

import java.util.Random;
import java.util.UUID;

public class Ticket {
    private String ticketId;
    private String userId; // Link to User
    private String source;
    private String destination;
    private String dateOfTravel;
    private Train train; // Linked Train

    private int seatRow;
    private int seatCol;

    // Add getters and setters
    public int getSeatRow() {
        return seatRow;
    }
    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }
    public int getSeatCol() {
        return seatCol;
    }
    public void setSeatCol(int seatCol) {
        this.seatCol = seatCol;
    }
    public Ticket() {
    }

    public Ticket(String source, String destination, String dateOfTravel, Train train, String userId) {
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
        this.userId = userId;
        this.ticketId = generateTicketId(source, destination);
    }

    private String generateTicketId(String source, String destination) {
        String src = source.substring(0, 2).toUpperCase();
        String dest = destination.substring(0, 2).toUpperCase();
        String randomDigits = UUID.randomUUID().toString().replaceAll("[^0-9]", "");

        while (randomDigits.length() < 4) {
            randomDigits += new Random().nextInt(10);
        }

        return src + dest + randomDigits.substring(0, 4);
    }

    // Getters and setters
    public String getTicketId() {
        return ticketId;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }

    public Train getTrain() {
        return train;
    }

    public String getUserId() {
        return userId;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}