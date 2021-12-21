package gr.kariera.codingschool.mindthecode.entities;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "engine")
@TypeAlias("hydrogen")
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