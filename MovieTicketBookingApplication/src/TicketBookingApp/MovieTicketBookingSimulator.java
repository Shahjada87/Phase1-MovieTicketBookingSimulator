package TicketBookingApp;

import java.util.*;

class Seat {
    private boolean isBooked;

    public Seat() {
        this.isBooked = false;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        this.isBooked = true;
    }
}

class MovieTheatre {
    private Map<String, Map<String, List<Seat>>> seatingArrangement; // Date -> ShowTime -> Seats

    public MovieTheatre() {
        this.seatingArrangement = new HashMap<>();
    }

    public void addShow(String date, String showTime, int rows, int seatsPerRow) {
        Map<String, List<Seat>> showSeats = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            List<Seat> seats = new ArrayList<>();
            for (int j = 0; j < seatsPerRow; j++) {
                seats.add(new Seat());
            }
            showSeats.put("Row " + (i + 1), seats);
        }
        seatingArrangement.put(date + " " + showTime, showSeats);
    }

    public void displaySeatingArrangement(String date, String showTime) {
        Map<String, List<Seat>> showSeats = seatingArrangement.get(date + " " + showTime);
        if (showSeats != null) {
            for (Map.Entry<String, List<Seat>> entry : showSeats.entrySet()) {
                System.out.print(entry.getKey() + ": ");
                for (Seat seat : entry.getValue()) {
                    System.out.print(seat.isBooked() ? "X " : "O ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Invalid date or show time.");
        }
    }

    public boolean bookTicket(String date, String showTime, String seat) {
        Map<String, List<Seat>> showSeats = seatingArrangement.get(date + " " + showTime);
        if (showSeats != null) {
            for (List<Seat> seats : showSeats.values()) {
                for (Seat s : seats) {
                    if (seat.equals("Row " + (seats.indexOf(s) + 1))) {
                        if (!s.isBooked()) {
                            s.book();
                            System.out.println("Ticket booked successfully!");
                            return true;
                        } else {
                            System.out.println("Seat already booked. Please choose another seat.");
                            return false;
                        }
                    }
                }
            }
        }
        System.out.println("Invalid date, show time, or seat selection.");
        return false;
    }
}

public class MovieTicketBookingSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieTheatre movieTheatre = new MovieTheatre();

        // Add a show for demonstration
        movieTheatre.addShow("2024-01-01", "15:00", 5, 10);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Display Seating Arrangement");
            System.out.println("2. Book Ticket");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter date (yyyy-mm-dd): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter show time: ");
                    String showTime = scanner.nextLine();
                    movieTheatre.displaySeatingArrangement(date, showTime);
                    break;
                case 2:
                    System.out.print("Enter date (yyyy-mm-dd): ");
                    date = scanner.nextLine();
                    System.out.print("Enter show time: ");
                    showTime = scanner.nextLine();
                    System.out.print("Enter seat (e.g., Row 2): ");
                    String seat = scanner.nextLine();
                    movieTheatre.bookTicket(date, showTime, seat);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}