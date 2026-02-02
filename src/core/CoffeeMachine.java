package core;

import entity.BaseDrink;

import java.sql.SQLException;

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
    int getWater() throws SQLException;
    int getMilk();
    int getCoffeeBeans();

    void refillWater(int amount);
    void refillMilk(int amount);
    void refillBeans(int amount);
}
