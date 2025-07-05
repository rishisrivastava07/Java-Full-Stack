package ticket.booking.entities;

import java.util.List;
import java.util.Map;

public class Train {
    private String trainNo;
    private List<List<Integer>> seats; // 1 = available, 0 = booked
    private Map<String, String> stationTimes;
    private String arrivalTime;
    private String departureTime;


    public Train() {}

    public Train(String trainNo, List<List<Integer>> seats, Map<String, String> stationTimes,
                 String arrivalTime, String departureTime) {
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public String getTrainNo() { return trainNo; }
    public void setTrainNo(String trainNo) { this.trainNo = trainNo; }

    public List<List<Integer>> getSeats() { return seats; }
    public void setSeats(List<List<Integer>> seats) { this.seats = seats; }

    public Map<String, String> getStationTimes() { return stationTimes; }
    public void setStationTimes(Map<String, String> stationTimes) { this.stationTimes = stationTimes; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
}