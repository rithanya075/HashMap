package week2;

import java.util.*;

class ParkingSpot {
    String vehicleNumber;
    long entryTime;
    boolean occupied;

    ParkingSpot() {
        this.occupied = false;
    }
}

public class Problem8 {

    private ParkingSpot[] parkingLot;
    private int size;

    public Problem8(int capacity) {
        this.size = capacity;
        parkingLot = new ParkingSpot[capacity];

        for (int i = 0; i < capacity; i++) {
            parkingLot[i] = new ParkingSpot();
        }
    }

    // Hash function
    private int hash(String vehicleNumber) {
        return Math.abs(vehicleNumber.hashCode()) % size;
    }

    // Park vehicle
    public void parkVehicle(String vehicleNumber) {

        int index = hash(vehicleNumber);
        int startIndex = index;

        while (parkingLot[index].occupied) {
            index = (index + 1) % size;

            if (index == startIndex) {
                System.out.println("Parking Full!");
                return;
            }
        }

        parkingLot[index].vehicleNumber = vehicleNumber;
        parkingLot[index].entryTime = System.currentTimeMillis();
        parkingLot[index].occupied = true;

        System.out.println("Vehicle " + vehicleNumber + " parked at spot " + index);
    }

    // Exit vehicle
    public void exitVehicle(String vehicleNumber) {

        int index = hash(vehicleNumber);
        int startIndex = index;

        while (parkingLot[index].occupied) {

            if (parkingLot[index].vehicleNumber.equals(vehicleNumber)) {

                long duration = (System.currentTimeMillis() - parkingLot[index].entryTime) / 1000;

                parkingLot[index].occupied = false;

                System.out.println("Vehicle " + vehicleNumber + " exited from spot " + index);
                System.out.println("Duration: " + duration + " seconds");

                return;
            }

            index = (index + 1) % size;

            if (index == startIndex) break;
        }

        System.out.println("Vehicle not found!");
    }

    // Display status
    public void displayStatus() {
        for (int i = 0; i < size; i++) {
            if (parkingLot[i].occupied) {
                System.out.println("Spot " + i + ": " + parkingLot[i].vehicleNumber);
            } else {
                System.out.println("Spot " + i + ": EMPTY");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Problem8 system = new Problem8(5);

        system.parkVehicle("ABC123");
        system.parkVehicle("XYZ999");
        system.parkVehicle("CAR456");

        system.displayStatus();

        Thread.sleep(2000);

        system.exitVehicle("ABC123");

        system.displayStatus();
    }
}