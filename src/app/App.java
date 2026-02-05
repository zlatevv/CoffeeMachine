package app;

import core.CoffeeMachineImpl;
import entity.*;

import java.util.Scanner;

public class App {

    // ✅ valid coins in stotinki
    private static final int[] VALID_COINS = {10, 20, 50, 100, 200};

    public static void main(String[] args) {

        CoffeeMachineImpl machine = new CoffeeMachineImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Coffee Machine!");
        boolean running = true;

        while (running) {
            try {
                System.out.println("\nAvailable drinks:");
                System.out.println("1. Coffee - 0.60 euro");
                System.out.println("2. Latte - 0.80 euro");
                System.out.println("3. Cappuccino - 1.50 euro");
                System.out.println("4. Refill Ingredients");
                System.out.println("5. Exit");
                System.out.print("Select an option (1-5): ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a number!");
                    scanner.nextLine();
                    continue;
                }

                int choice = scanner.nextInt();

                BaseDrink selectedDrink;

                switch (choice) {
                    case 1 -> selectedDrink = new Coffee();
                    case 2 -> selectedDrink = new Latte();
                    case 3 -> selectedDrink = new Cappuccino();

                    case 4 -> {
                        refillMenu(machine, scanner);
                        continue;
                    }

                    case 5 -> {
                        running = false;
                        continue;
                    }

                    default -> {
                        System.out.println("Invalid choice!");
                        continue;
                    }
                }

                handleDrinkPurchase(machine, scanner, selectedDrink);

            } catch (Exception e) {
                System.out.println("Something went wrong. Try again.");
                scanner.nextLine(); // clear buffer
            }
        }

        System.out.println("Thank you for using the Coffee Machine!");
    }

    // -------------------------
    // DRINK PURCHASE
    // -------------------------
    private static void handleDrinkPurchase(CoffeeMachineImpl machine, Scanner scanner, BaseDrink drink) {

        double inserted = 0;
        double price = drink.getPrice();

        while (inserted < price) {

            double remaining = price - inserted;
            System.out.printf("You still need %.2f euro\n", remaining);
            System.out.print("Insert coin (0.10, 0.20, 0.50, 1, 2): ");

            if (!scanner.hasNextDouble()) {
                System.out.println("Invalid input!");
                scanner.nextLine();
                continue;
            }

            double coin = scanner.nextDouble();
            int stotinki = (int) Math.round(coin * 100);

            if (!isValidCoin(stotinki)) {
                System.out.println("Invalid coin!");
                continue;
            }

            machine.insertMoney(coin);
            inserted += coin;
        }

        // ingredient check
        if (!machine.canMakeDrink(drink)) {
            System.out.println("Not enough ingredients. Money returned.");
            return;
        }

        // make drink
        machine.makeDrink(drink, inserted);

        System.out.printf("Machine balance: %.2f euro\n", machine.getCurrentBalance());
        System.out.printf("Remaining ingredients - Water: %d ml, Milk: %d ml, Beans: %d g\n",
                machine.getWater(),
                machine.getMilk(),
                machine.getCoffeeBeans());
    }

    // -------------------------
    // REFILL MENU
    // -------------------------
    private static void refillMenu(CoffeeMachineImpl machine, Scanner scanner) {
        try {
            System.out.print("Refill Water (ml): ");
            int water = scanner.nextInt();
            machine.refillWater(water);

            System.out.print("Refill Milk (ml): ");
            int milk = scanner.nextInt();
            machine.refillMilk(milk);

            System.out.print("Refill Coffee Beans (g): ");
            int beans = scanner.nextInt();
            machine.refillBeans(beans);

            System.out.println("Ingredients updated!");
            System.out.printf("Water: %d ml, Milk: %d ml, Beans: %d g\n",
                    machine.getWater(),
                    machine.getMilk(),
                    machine.getCoffeeBeans());

        } catch (Exception e) {
            System.out.println("Invalid refill input!");
            scanner.nextLine();
        }
    }

    // -------------------------
    // HELPERS
    // -------------------------
    private static boolean isValidCoin(int coin) {
        for (int c : VALID_COINS) {
            if (c == coin) return true;
        }
        return false;
    }
}
