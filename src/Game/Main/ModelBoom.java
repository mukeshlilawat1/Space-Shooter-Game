package src.Game.Main;

public class ModelBoom {
    private double size;
    private float angle;

    public double getSize() {
        return size;
    }

    public void setSizs(double size) {
        this.size = size;
    }

    public float getAngle() {
        return angle;
    }

    public float setAngle(float angle) {
        return this.angle = angle;
    }

    public ModelBoom(double size, float angle) {
        this.size = size;
        this.angle = angle;
    }

    public ModelBoom() {

    }

}