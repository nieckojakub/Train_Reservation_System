package train.train;

public class Ticket {

    private int ticket_id;
    /////////////////
    private String origin;
    private String destination;
    private String id_train;
    private String price;
    private String date;
    private String departure_time;
    private String arival_time;
    private String owner;
    /////////////////

    public Ticket(int ticketId, String id_train, String origin, String destination, String price, String date,
                  String departureTime, String arrivalTime, String owner) {
        this.ticket_id = ticketId;
        this.id_train = id_train;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.date = date;
        this.departure_time = departureTime;
        this.arival_time = arrivalTime;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
}
