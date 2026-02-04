package entity;

import java.util.Map;

public abstract class BaseDrink {
    private Double price;
    private String name;
    private Map<Ingredient, Integer> neededIngredients;

    public BaseDrink(String name, Double price, Map<Ingredient, Integer> neededIngredients) {
        this.name = name;
        this.price = price;
        this.neededIngredients = neededIngredients;
    }

    public abstract void prepare();

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Map<Ingredient, Integer> getNeedIngredients() {
        return neededIngredients;
    }
}
