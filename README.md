# Coffee Machine (Java OOP)

Simple console-based coffee machine simulator that demonstrates OOP concepts
like abstraction, inheritance, and interfaces, plus basic persistence via MySQL.

## Features
- Choose drinks (Coffee, Latte, Cappuccino)
- Insert coins and receive change
- Track ingredient levels and refill stock
- Persist ingredients and cash in a MySQL database

## Project Structure
```
src/
  app/      # console UI / entry point
  core/     # coffee machine logic + DB access
  entity/   # drinks and ingredient models
```

## Requirements
- Java 17+ (or any Java version that supports the `switch` arrow syntax)
- MySQL server running locally

## Database Setup
1. Create database:
```sql
CREATE DATABASE coffee_machine;
```

2. Create tables:
```sql
CREATE TABLE ingredients (
  name VARCHAR(50) PRIMARY KEY,
  quantity INT NOT NULL
);

CREATE TABLE cash (
  denomination INT PRIMARY KEY,
  quantity INT NOT NULL
);
```

3. Seed initial data:
```sql
INSERT INTO ingredients (name, quantity) VALUES
  ('water', 1000),
  ('milk', 1000),
  ('coffeeBeans', 500);

INSERT INTO cash (denomination, quantity) VALUES
  (200, 10),
  (100, 20),
  (50, 30),
  (20, 30),
  (10, 50);
```

4. Update credentials if needed:
```
src/core/DB.java
```

## Run
Compile and run from the project root:
```
javac -d out $(find src -name "*.java")
java -cp out app.App
```

## Notes for OOP Explanation
- `CoffeeMachine` is an interface; `CoffeeMachineImpl` provides the implementation.
- `BaseDrink` is an abstract class; `Coffee`, `Latte`, `Cappuccino` extend it.
- Ingredients are modeled with an `Ingredient` enum for type safety.

