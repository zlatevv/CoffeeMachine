package entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class Latte extends BaseDrink {
    private static final String NAME = "Latte";
    private static final Double PRICE = 0.80;
    private static final Map<Ingredient, Integer> INGREDIENTS = new LinkedHashMap<>();

    public Latte() {
        super(NAME, PRICE, INGREDIENTS);
    }

    static {
        INGREDIENTS.put(Ingredient.WATER, 100);
        INGREDIENTS.put(Ingredient.MILK, 150);
        INGREDIENTS.put(Ingredient.COFFEE_BEANS, 18);
    }

    @Override
    public void prepare() {
        System.out.println("Preparing a latte ...");

        INGREDIENTS.forEach(
                (name, value) -> System.out.printf("%d of %s\n", value, name.getDisplayName())
        );

        System.out.println("Latte made!");
    }
}
