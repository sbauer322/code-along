package tv.twitch.nicholai518.ch10;

public class ChapterTenProblemTen {
    public void run() {
        Ship[] ships = new Ship[5];
        ships[0] = new Ship("Alaska", "1997");
        ships[1] = new CruiseShip("Nevada", "1985", 500);
        ships[2] = new CargoShip("Washington", "2011", 1000);
        ships[3] = new CruiseShip("Maine", "1962", 200);
        ships[4] = new CargoShip("Rhode Island", "2020", 450);

        for (Ship ship : ships) {
            System.out.println(ship);
        }
    }
}
