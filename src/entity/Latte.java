package entity;

import java.util.LinkedHashMap;

public class Latte extends BaseDrink {
    private static final String NAME = "Latte";
    private static final Double PRICE = 2.50;
    private static final LinkedHashMap<String, Integer> INGREDIENTS = new LinkedHashMap<>();

    public Latte() {
        super(NAME, PRICE, INGREDIENTS);
    }

    static {
        INGREDIENTS.put("water", 100);
        INGREDIENTS.put("milk", 150);
        INGREDIENTS.put("coffeeBeans", 18);
    }

    @Override
    public void prepare() {
        System.out.println("Preparing a latte ...");

        INGREDIENTS.forEach(
                (name, value) -> System.out.printf("%d of %s\n", value, name)
        );

        System.out.println("Latte made!");
    }
}
