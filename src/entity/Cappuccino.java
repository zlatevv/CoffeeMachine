package entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cappuccino extends BaseDrink {
    private static final String NAME = "Cappuccino";
    private static final Double PRICE = 1.50;
    private static final Map<Ingredient, Integer> INGREDIENTS = new LinkedHashMap<>();

    public Cappuccino() {
        super(NAME, PRICE, INGREDIENTS);
    }

    static {
        INGREDIENTS.put(Ingredient.WATER, 100);
        INGREDIENTS.put(Ingredient.MILK, 120);
        INGREDIENTS.put(Ingredient.COFFEE_BEANS, 18);
    }

    @Override
    public void prepare() {
        System.out.println("Preparing a cappuccino ...");

        INGREDIENTS.forEach(
                (name, value) -> System.out.printf("%d of %s\n", value, name.getDisplayName())
        );

        System.out.println("Cappuccino made!");
    }
}
