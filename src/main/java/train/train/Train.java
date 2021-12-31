package train.train;

import java.time.LocalDate;

public class Train {

    private String origin;
    private String destination;
    private String id_train;
    private String price;
    private LocalDate date;
    private String departure_time;
    private String arival_time;
    private String train_number;

    public Train(String id_train, String origin, String destination, String train_number, String price, LocalDate date, String departureTime, String arrivalTime) {
        this.id_train = id_train;
        this.origin = origin;
        this.destination = destination;
        this.train_number = train_number;
        this.price = price;
        this.date = date;
        this.departure_time = departureTime;
        this.arival_time = arrivalTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getId_train() {
        return id_train;
    }

    public void setId_train(String id_train) {
        this.id_train = id_train;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArival_time() {
        return arival_time;
    }

    public void setArival_time(String arival_time) {
        this.arival_time = arival_time;
    }

    public String getTrain_number() {
        return train_number;
    }

    public void setTrain_number(String train_number) {
        this.train_number = train_number;
    }
}
