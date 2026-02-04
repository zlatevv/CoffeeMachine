package core;

import entity.BaseDrink;
import entity.Ingredient;
import java.sql.*;
import java.util.Map;

public class CoffeeMachineImpl implements CoffeeMachine {

    private double currentBalance = 0;
    private final int[] nominals = {200, 100, 50, 20, 10}; // in stotinki (1 lv = 100 stotinki)

    // --------------------
    // MONEY HELPERS
    // --------------------
    private int getCoinQuantity(int denomination) {
        String sql = "SELECT quantity FROM cash WHERE denomination = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, denomination);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("quantity");
        } catch (SQLException e) {
            System.err.println("DB error (getCoinQuantity): " + e.getMessage());
        }
        return 0;
    }

    private void addCoin(int denomination, int amount) {
        String sql = "INSERT INTO cash (denomination, quantity) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, denomination);
            ps.setInt(2, amount);
            ps.setInt(3, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DB error (addCoin): " + e.getMessage());
        }
    }

    private void removeCoin(int denomination, int amount) {
        String sql = "UPDATE cash SET quantity = quantity - ? WHERE denomination = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setInt(2, denomination);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DB error (removeCoin): " + e.getMessage());
        }
    }

    @Override
    public void insertMoney(double amount) {
        int stotinki = (int)Math.round(amount * 100);
        int index = 0;
        while (stotinki >= 10 && index < nominals.length) {
            if (stotinki >= nominals[index]) {
                addCoin(nominals[index], 1);
                stotinki -= nominals[index];
            } else {
                index++;
            }
        }
        currentBalance += amount;
    }

    @Override
    public boolean checkIfMoneyIsEnough(BaseDrink drink, double amount) {
        return amount >= drink.getPrice();
    }

    @Override
    public double calculateChange(BaseDrink drink, double amount) {
        return amount - drink.getPrice();
    }

    @Override
    public void returnChange(BaseDrink drink, double change) {
        int cents = (int)Math.round(change * 100);
        int index = 0;

        while (cents >= 10 && index < nominals.length) {
            int coinQty = getCoinQuantity(nominals[index]);
            if (cents >= nominals[index] && coinQty > 0) {
                removeCoin(nominals[index], 1);
                cents -= nominals[index];
                System.out.println("Returning: " + (nominals[index]/100.0) + " lv");
            } else {
                index++;
            }
        }

        if (cents > 0) {
            System.out.println("Cannot return exact change. Remaining: " + (cents / 100.0) + " lv");
        }

        currentBalance -= change;
    }

    @Override
    public double getCurrentBalance() {
        return currentBalance;
    }

    // --------------------
    // INGREDIENT HELPERS
    // --------------------
    private int getIngredientQuantity(Ingredient ingredient) {
        String sql = "SELECT quantity FROM ingredients WHERE name = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ingredient.getDbName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("quantity");
        } catch (SQLException e) {
            System.err.println("DB error (getIngredientQuantity): " + e.getMessage());
        }
        return 0;
    }

    private void updateIngredient(Ingredient ingredient, int change) {
        String sql = "UPDATE ingredients SET quantity = quantity + ? WHERE name = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, change);
            ps.setString(2, ingredient.getDbName());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DB error (updateIngredient): " + e.getMessage());
        }
    }

    @Override
    public int getWater() { return getIngredientQuantity(Ingredient.WATER); }
    @Override
    public int getMilk() { return getIngredientQuantity(Ingredient.MILK); }
    @Override
    public int getCoffeeBeans() { return getIngredientQuantity(Ingredient.COFFEE_BEANS); }

    @Override
    public void refillWater(int amount) { updateIngredient(Ingredient.WATER, amount); }
    @Override
    public void refillMilk(int amount) { updateIngredient(Ingredient.MILK, amount); }
    @Override
    public void refillBeans(int amount) { updateIngredient(Ingredient.COFFEE_BEANS, amount); }

    // --------------------
    // DRINK LOGIC
    // --------------------
    @Override
    public boolean canMakeDrink(BaseDrink drink) {
        for (Map.Entry<Ingredient, Integer> entry : drink.getNeedIngredients().entrySet()) {
            int available = getIngredientQuantity(entry.getKey());
            if (available < entry.getValue()) {
                System.out.printf("Not enough %s! Need %d more.\n",
                        entry.getKey().getDisplayName(), entry.getValue() - available);
                return false;
            }
        }
        return true;
    }

    @Override
    public void makeDrink(BaseDrink drink, double amount) {
        if (!checkIfMoneyIsEnough(drink, amount)) {
            System.out.println("Not enough money!");
            return;
        }

        if (!canMakeDrink(drink)) {
            System.out.println("Cannot make drink due to insufficient ingredients.");
            return;
        }

        drink.prepare();
        insertMoney(amount);

        // give change
        double change = calculateChange(drink, amount);
        if (change > 0) returnChange(drink, change);

        // subtract ingredients in DB
        for (Map.Entry<Ingredient, Integer> entry : drink.getNeedIngredients().entrySet()) {
            updateIngredient(entry.getKey(), -entry.getValue());
        }
    }
}
