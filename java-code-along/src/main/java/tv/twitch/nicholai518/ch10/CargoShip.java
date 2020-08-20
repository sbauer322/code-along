package tv.twitch.nicholai518.ch10;

public class CargoShip extends Ship {
    private int cargoCapacity;

    public CargoShip(String name, String yearBuilt, int cargoCapacity) {
        super(name, yearBuilt);
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public String toString() {
        return "CargoShip{" +
                "name='" + name + '\'' +
                ", cargoCapacity='" + cargoCapacity + '\'' +
                '}';
    }
}
