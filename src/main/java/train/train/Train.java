package train.train;

public class Train {


    private String departureStation;
    private String destinatonStation;
    private String trainNumber;
    private String priceNormal;
    private String priceChild;
    private String date;
    private String id_train;
    private String departure_time;
    private String arival_time;



    public Train( String departureStation, String destinatonStation, String trainNumber,
                 String priceNormal, String priceChild, String date, String id_train,String departure_time, String arival_time) {
        this.departureStation = departureStation;
        this.destinatonStation = destinatonStation;
        this.trainNumber = trainNumber;
        this.priceNormal = priceNormal;
        this.priceChild = priceChild;
        this.date = date;
        this.id_train = id_train;
        this.departure_time=departure_time;
        this.arival_time=arival_time;
    }


    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getDestinatonStation() {
        return destinatonStation;
    }

    public void setDestinatonStation(String destinatonStation) {
        this.destinatonStation = destinatonStation;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getPriceNormal() {
        return priceNormal;
    }

    public void setPriceNormal(String priceNormal) {
        this.priceNormal = priceNormal;
    }

    public String getPriceChild() {
        return priceChild;
    }

    public void setPriceChild(String priceChild) {
        this.priceChild = priceChild;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_train() {
        return id_train;
    }

    public void setId_train(String id_train) {
        this.id_train = id_train;
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
