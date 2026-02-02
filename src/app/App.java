package app;

import core.CoffeeMachineImpl;
import entity.BaseDrink;
import entity.Coffee;
import entity.Latte;
import entity.Cappuccino;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
        CoffeeMachineImpl machine = new CoffeeMachineImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Coffee Machine!");
        boolean running = true;

        while (running) {
            System.out.println("\nAvailable drinks:");
            System.out.println("1. Coffee - 0.60 euro");
            System.out.println("2. Latte - 0.80 euro");
            System.out.println("3. Cappuccino - 1.50 euro");
            System.out.println("4. Refill Ingredients");
            System.out.println("5. Exit");
            System.out.print("Select an option (1-5): ");
            int choice = scanner.nextInt();

            BaseDrink selectedDrink = null;

            switch (choice) {
                case 1 -> selectedDrink = new Coffee();
                case 2 -> selectedDrink = new Latte();
                case 3 -> selectedDrink = new Cappuccino();
                case 4 -> {
                    System.out.print("Refill Water (ml): ");
                    int water = scanner.nextInt();
                    machine.refillWater(water);

                    System.out.print("Refill Milk (ml): ");
                    int milk = scanner.nextInt();
                    machine.refillMilk(milk);

                    System.out.print("Refill Coffee Beans (g): ");
                    int beans = scanner.nextInt();
                    machine.refillBeans(beans);

                    System.out.println("Ingredients updated successfully!");
                    System.out.printf("Current ingredients - Water: %d ml, Milk: %d ml, Beans: %d g\n",
                            machine.getWater(), machine.getMilk(), machine.getCoffeeBeans());
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

            double inserted = 0;
            double price = selectedDrink.getPrice();

            while (inserted < price) {
                double remaining = price - inserted;
                System.out.printf("You still need %.2f euro\n", remaining);
                System.out.print("Insert coin (0.10, 0.20, 0.50, 1, 2): ");
                double coin = scanner.nextDouble();

                int stotinki = (int)Math.round(coin * 100);
                int[] validCoins = {10, 20, 50, 100, 200};
                boolean valid = false;

                for (int c : validCoins) {
                    if (stotinki == c) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    System.out.println("Invalid coin! Please insert a valid denomination.");
                    continue;
                }

                // Insert coin into machine
                machine.insertMoney(coin);
                inserted += coin;
            }

            // Check if enough ingredients
            if (!machine.canMakeDrink(selectedDrink)) {
                System.out.println("Cannot make drink. Transaction cancelled.");
                continue;
            }

            // Make the drink
            machine.makeDrink(selectedDrink, inserted);

            System.out.printf("Current machine balance: %.2f euro\n", machine.getCurrentBalance());
            System.out.printf("Remaining ingredients - Water: %d ml, Milk: %d ml, Beans: %d g\n",
                    machine.getWater(), machine.getMilk(), machine.getCoffeeBeans());
        }

        System.out.println("Thank you for using the Coffee Machine!");
        scanner.close();
    }
}
