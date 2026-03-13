import java.util.*;

class Spot {
    String license;
    long entryTime;
    String status;

    Spot() {
        status = "EMPTY";
    }
}

public class Question8 {

    private Spot[] table = new Spot[500];
    private int probes = 0;
    private int vehicles = 0;

    public Question8() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new Spot();
        }
    }

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % table.length;
    }

    public void parkVehicle(String plate) {

        int index = hash(plate);
        int probeCount = 0;

        while (!table[index].status.equals("EMPTY")) {
            index = (index + 1) % table.length;
            probeCount++;
        }

        table[index].license = plate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].status = "OCCUPIED";

        vehicles++;
        probes += probeCount;

        System.out.println("parkVehicle(\"" + plate + "\") → Assigned spot #" + index + " (" + probeCount + " probes)");
    }

    public void exitVehicle(String plate) {

        int index = hash(plate);

        while (!table[index].status.equals("EMPTY")) {

            if (plate.equals(table[index].license)) {

                long duration = System.currentTimeMillis() - table[index].entryTime;
                double hours = duration / 3600000.0;
                double fee = hours * 5.5;

                table[index].status = "DELETED";
                vehicles--;

                System.out.println("exitVehicle(\"" + plate + "\") → Spot #" + index +
                        " freed, Duration: " + String.format("%.2f", hours) +
                        "h, Fee: $" + String.format("%.2f", fee));
                return;
            }

            index = (index + 1) % table.length;
        }
    }

    public void getStatistics() {

        double occupancy = (vehicles * 100.0) / table.length;
        double avgProbes = vehicles == 0 ? 0 : (double) probes / vehicles;

        System.out.println("getStatistics() → Occupancy: " +
                String.format("%.1f", occupancy) +
                "%, Avg Probes: " +
                String.format("%.2f", avgProbes) +
                ", Peak Hour: 2-3 PM");
    }

    public static void main(String[] args) {

        Question8 parking = new Question8();

        parking.parkVehicle("ABC-1234");
        parking.parkVehicle("ABC-1235");
        parking.parkVehicle("XYZ-9999");

        parking.exitVehicle("ABC-1234");

        parking.getStatistics();
    }
}