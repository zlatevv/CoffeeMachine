package entity;

import java.util.LinkedHashMap;

public class Coffee extends BaseDrink{
    private static final String NAME = "Coffee";
    private static final Double PRICE = 1.20;
    private static final LinkedHashMap<String, Integer> INGREDIENTS = new LinkedHashMap<>();

    public Coffee() {
        super(NAME, PRICE, INGREDIENTS);
    }

    static {
        INGREDIENTS.put("water", 200);
        INGREDIENTS.put("coffeeBeans", 15);
    }

    @Override
    public void prepare() {
        System.out.println("Preparing a coffee ...");

        INGREDIENTS.forEach(
                (name, value) -> System.out.printf("%d of %s\n", value, name)
        );

        System.out.println("Coffee made!");
    }
}
