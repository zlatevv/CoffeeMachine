package core;

import entity.BaseDrink;

import java.util.LinkedHashMap;
import java.util.Map;

public class CoffeeMachineImpl implements CoffeeMachine{
    private LinkedHashMap<Integer, Integer> money = new LinkedHashMap<>();;
    private LinkedHashMap<String, Integer> ingredients = new LinkedHashMap<>();;
    private double currentBalance = 0;
    int[] nominals = {200, 100, 50, 20, 10};

    public CoffeeMachineImpl() {
        money.put(200, 2);
        money.put(100, 5);
        money.put(50, 10);
        money.put(20, 10);
        money.put(10, 20);

        ingredients.put("water", 2000);       // 2000 ml
        ingredients.put("milk", 1000);        // 1000 ml
        ingredients.put("coffeeBeans", 500);  // 500 g
    }

    @Override
    public void insertMoney(double amount) {
        int index = 0;
        int stotinki = Math.toIntExact(Math.round(amount * 100));

        while (stotinki >= 10 && index < nominals.length) {
            if (stotinki >= nominals[index]) {
                money.put(nominals[index], money.getOrDefault(nominals[index], 0) + 1);
                stotinki -= nominals[index];
            }else {
                index++;
            }
        }

        currentBalance += amount;
    }

    @Override
    public boolean checkIfMoneyIsEnough(BaseDrink drink, double amount) {
        return drink.getPrice() <= amount;
    }

    @Override
    public double calculateChange(BaseDrink drink, double amount) {
        return amount - drink.getPrice();
    }

    @Override
    public void returnChange(BaseDrink drink, double change) {
        int stotinki = (int) Math.round(change * 100);
        int index = 0;

        while (stotinki >= 10 && index < nominals.length) {
            if (stotinki >= nominals[index] && money.getOrDefault(nominals[index], 0) > 0) {
                money.put(nominals[index], money.get(nominals[index]) - 1);
                stotinki -= nominals[index];
                System.out.println("Returning: " + (nominals[index]/100.0) + " lv");
            } else {
                index++;
            }
        }

        if (stotinki > 0) {
            System.out.println("Machine cannot return exact change! Remaining: " + (stotinki / 100.0) + " lv");
        }

        currentBalance -= change;
    }

    @Override
    public double getCurrentBalance() {
        return currentBalance;
    }

    @Override
    public boolean canMakeDrink(BaseDrink drink) {
        for (Map.Entry<String, Integer> entry : drink.getNeedIngredients().entrySet()) {
            String ingredientName = entry.getKey();
            int required = entry.getValue();
            int available = ingredients.getOrDefault(ingredientName, 0);

            if (available < required) {
                System.out.printf(
                        "You don't have enough %s!\nYou need %d more!\n",
                        ingredientName,
                        required - available
                );
                return false;
            }
        }
        return true;
    }

    @Override
    public void makeDrink(BaseDrink drink, double amount) {
        if (canMakeDrink(drink)){
            if (checkIfMoneyIsEnough(drink, amount)){
                drink.prepare();
                insertMoney(amount);

                double change = calculateChange(drink, amount);
                if (change > 0){
                    returnChange(drink, change);
                }
                drink.getNeedIngredients().forEach(
                        (name, value) -> ingredients.put(name, ingredients.get(name) - value)
                );
            }
        }
    }

    @Override
    public int getWater() {
        return ingredients.getOrDefault("water", 0);
    }

    @Override
    public int getMilk() {
        return ingredients.getOrDefault("milk", 0);
    }

    @Override
    public int getCoffeeBeans() {
        return ingredients.getOrDefault("coffeeBeans", 0);
    }

    @Override
    public void refillWater(int amount) {
        ingredients.put("water", ingredients.getOrDefault("water", 0) + amount);
    }

    @Override
    public void refillMilk(int amount) {
        ingredients.put("milk", ingredients.getOrDefault("milk", 0) + amount);
    }

    @Override
    public void refillBeans(int amount) {
        ingredients.put("coffeeBeans", ingredients.getOrDefault("coffeeBeans", 0) + amount);
    }
}
