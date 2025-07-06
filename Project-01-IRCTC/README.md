# IRCTC Booking Application

A console-based Java application simulating an IRCTC-like railway ticket booking system. This project allows users to sign up, log in, and book or cancel train tickets, while admins can manage users and train routes.

---

## Project Structure

```
Project-01-IRCTC
├── app
│   ├── src
│   │   └── main
│   │       ├── java
│   │       │   └── ticket.booking
│   │       │       ├── entities        # POJOs (Ticket, Train, User)
│   │       │       ├── localDb         # Simulated local database (JSON)
│   │       │       ├── services        # Business logic (TrainService, UserService)
│   │       │       ├── util            # Utility classes
│   │       │       └── App.java        # Main class with menu loop
│   └── build.gradle                    # Gradle build file
```

---

## Features

### User Service
- **Login**: Authenticate existing users.
- **Signup**: Register new users with secure password handling.
- **View Booked Tickets**: Display all tickets booked by the logged-in user.
- **Cancel Ticket**: Allows a user to cancel their booked ticket.

### Train Service
- **Book Ticket**: Reserve a seat on a selected train route.
- **Create Train Route**: Admin-only feature to create a new train with source, destination, and schedule.
- **List of Users (Admin)**: Admin-only feature to view all registered users.

---

## Menu Options

When the application runs, the following menu is presented:

```
1. Login
2. Signup
3. List of Booked Tickets
4. Book Ticket
5. Cancel Ticket
6. Create Train Route
7. List of Users (Admin)
8. Exit
```

---

## 🧪 Sample Data

Stored in JSON files under `localDb/`:

- `users.json`: Stores user credentials and profiles.
- `trains.json`: Stores train route and seat availability data.

---

## Technologies Used

- **Java** (Core)
- **Gradle** (Build Tool)
- **Jackson** (for JSON serialization/deserialization)
- **BCrypt** (for password hashing)

---

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/your-username/IRCTC-Booking-System.git
cd IRCTC-Booking-System/app
```

### Run the Application

1. Open the project in IntelliJ IDEA or any Java-compatible IDE.
2. Build the project using Gradle.
3. Run the `App.java` class from `ticket.booking` package.
4. Follow the console instructions to interact with the system.

---

## Author

Developed by **Rishi Srivastava** as part of a Java full-stack learning journey.

---

## 📄 License

This project is for educational purposes only.
