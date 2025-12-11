package entity;

import java.util.LinkedHashMap;

public abstract class BaseDrink {
    private Double price;
    private String name;
    private LinkedHashMap<String, Integer> neededIngredients;

    public BaseDrink(String name, Double price, LinkedHashMap<String, Integer> neededIngredients) {
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

    public LinkedHashMap<String, Integer> getNeedIngredients() {
        return neededIngredients;
    }
}
