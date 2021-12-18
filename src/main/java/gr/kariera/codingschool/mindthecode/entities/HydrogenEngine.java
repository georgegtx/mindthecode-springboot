package gr.kariera.codingschool.mindthecode.entities;

import javax.persistence.Entity;

@Entity
public class HydrogenEngine extends Engine {
    private double compressionRatio;

    public HydrogenEngine() {
    }

    public HydrogenEngine(String serialNumber, int size, Car car, double compressionRatio) {
        super(serialNumber, size, car);
        this.compressionRatio = compressionRatio;
    }

    public double getCompressionRatio() {
        return compressionRatio;
    }

    public void setCompressionRatio(double compressionRatio) {
        this.compressionRatio = compressionRatio;
    }
}