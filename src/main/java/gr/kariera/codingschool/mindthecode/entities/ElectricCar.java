package gr.kariera.codingschool.mindthecode.entities;

import javax.persistence.Entity;

class ChargingSocket {
    public static String Type2 = "Type2";
    public static String Menekes = "Menekes";
}


@Entity
public class ElectricCar extends Car {

    private int batteryLevel;
    private String socket;

    public ElectricCar(){ }

    public ElectricCar(int mileage, String maker, String serialNumber, int batteryLevel, String socket) {
        super(mileage, maker, serialNumber);
        this.batteryLevel = batteryLevel;
        this.socket = socket;
    }

    public ElectricCar(int mileage, String maker, String serialNumber, int batteryLevel, String socket, Engine engine) {
        super(mileage, maker, serialNumber, engine);
        this.batteryLevel = batteryLevel;
        this.socket = socket;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        if (socket != ChargingSocket.Type2 || socket != ChargingSocket.Menekes)
            throw new UnsupportedOperationException("Charging socket must be of type Type2 | Menekes");
        this.socket = socket;
    }


}