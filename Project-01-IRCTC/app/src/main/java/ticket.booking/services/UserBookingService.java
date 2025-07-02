package main.java.ticket.booking.services;

import main.java.ticket.booking.entities.User;

import java.io.File;

public class UserBookingService {
    private User user;

    private
    private static final String USERS_PATH = "../localDb/users.json";

    public UserBookingService(User user) {
        this.user = user;
        File users = new File(USERS_PATH);
    }


    //  {
    //    "name": "rahul",
    //    "password": "hhbb11",
    //    "hashed_password": "$2a$10$O/OWE.xh3gttMANiM4BIX.mo.4/myik1J739DZ25ecVENk8xhMCW2",
    //    "tickets_booked": [],
    //    "user_id": "fe46da4d-c8d1-4229-97e6-600dce6941e8"
    //  }
}
