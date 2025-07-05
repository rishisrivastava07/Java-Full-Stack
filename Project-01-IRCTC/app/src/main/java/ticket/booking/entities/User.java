package ticket.booking.entities;

import ticket.booking.util.UserServiceUtil;
import java.util.List;
import java.util.UUID;

public class User {

    private String username;
    private String fullName;
    private String password;
    private String emailId;
    private String phoneNo;
    private String hashedPassword;
    private String userId;
    private List<Ticket> ticketsBooked;

    public User() {
    }

    public User(String username, String fullName, String password, String emailId, String phoneNo) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.hashedPassword = UserServiceUtil.hashPassword(password);
        this.userId = String.valueOf(UUID.randomUUID());
        this.ticketsBooked = new java.util.ArrayList<>();
    }

    // Standard getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void bookTicket(Ticket ticket) {
        ticketsBooked.add(ticket);
    }
}
