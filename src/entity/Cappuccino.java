package entity;

import java.util.LinkedHashMap;

public class Cappuccino extends BaseDrink {
    private static final String NAME = "Cappuccino";
    private static final Double PRICE = 1.50;
    private static final LinkedHashMap<String, Integer> INGREDIENTS = new LinkedHashMap<>();

    public Cappuccino() {
        super(NAME, PRICE, INGREDIENTS);
    }

    static {
        INGREDIENTS.put("water", 100);
        INGREDIENTS.put("milk", 120);
        INGREDIENTS.put("coffeeBeans", 18);
    }

    @Override
    public void prepare() {
        System.out.println("Preparing a cappuccino ...");

        INGREDIENTS.forEach(
                (name, value) -> System.out.printf("%d of %s\n", value, name)
        );

        System.out.println("Cappuccino made!");
    }
}

