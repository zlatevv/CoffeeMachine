package entity;

public enum Ingredient {
    WATER("water", "Water"),
    MILK("milk", "Milk"),
    COFFEE_BEANS("coffeeBeans", "Coffee Beans");

    private final String dbName;
    private final String displayName;

    Ingredient(String dbName, String displayName) {
        this.dbName = dbName;
        this.displayName = displayName;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
