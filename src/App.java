package app;

import core.CoffeeMachineImpl;
import entity.BaseDrink;
import entity.Coffee;
import entity.Latte;
import entity.Cappuccino;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        CoffeeMachineImpl machine = new CoffeeMachineImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Coffee Machine!");
        boolean running = true;

        while (running) {
            System.out.println("\nAvailable drinks:");
            System.out.println("1. Coffee - 1.20 lv");
            System.out.println("2. Latte - 1.50 lv");
            System.out.println("3. Cappuccino - 1.70 lv");
            System.out.println("4. Exit");
            System.out.print("Select a drink (1-4): ");
            int choice = scanner.nextInt();

            BaseDrink selectedDrink = null;
            switch (choice) {
                case 1 -> selectedDrink = new Coffee();
                case 2 -> selectedDrink = new Latte();
                case 3 -> selectedDrink = new Cappuccino();
                case 4 -> {
                    running = false;
                    continue;
                }
                default -> {
                    System.out.println("Invalid choice!");
                    continue;
                }
            }

            System.out.print("Insert money (lv): ");
            double money = scanner.nextDouble();

            // Check if enough money
            if (!machine.checkIfMoneyIsEnough(selectedDrink, money)) {
                System.out.println("Not enough money! Transaction cancelled.");
                continue;
            }

            // Check if enough ingredients
            if (!machine.canMakeDrink(selectedDrink)) {
                System.out.println("Cannot make drink. Transaction cancelled.");
                continue;
            }

            // Make the drink
            machine.makeDrink(selectedDrink, money);

            System.out.printf("Current machine balance: %.2f lv\n", machine.getCurrentBalance());
            System.out.printf("Remaining ingredients - Water: %d ml, Milk: %d ml, Beans: %d g\n",
                    machine.getWater(), machine.getMilk(), machine.getCoffeeBeans());
        }

        System.out.println("Thank you for using the Coffee Machine!");
        scanner.close();
    }
}
