package core;

import entity.BaseDrink;

public interface CoffeeMachine {
    // --- MONEY ---
    void insertMoney(double amount);
    boolean checkIfMoneyIsEnough(BaseDrink drink, double amount);
    double calculateChange(BaseDrink drink, double amount);
    void returnChange(BaseDrink drink, double amount);
    double getCurrentBalance();

    // --- DRINK SELECTION / MAKING ---
    boolean canMakeDrink(BaseDrink drink);
    void makeDrink(BaseDrink drink, double amount);

    // --- INGREDIENT MANAGEMENT ---
    int getWater();
    int getMilk();
    int getCoffeeBeans();

    void refillWater(int amount);
    void refillMilk(int amount);
    void refillBeans(int amount);
}
