package machine;

public enum Coffee {
    EXPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    private final int waterNeeded;
    private final int milkNeeded;
    private final int beansNeeded;
    private final int coffeePrice;

    Coffee(int waterNeeded, int milkNeeded, int beansNeeded, int coffeePrice) {
        this.waterNeeded = waterNeeded;
        this.milkNeeded = milkNeeded;
        this.beansNeeded = beansNeeded;
        this.coffeePrice = coffeePrice;
    }

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public int getMilkNeeded() {
        return milkNeeded;
    }

    public int getBeansNeeded() {
        return beansNeeded;
    }

    public int getCoffeePrice() {
        return coffeePrice;
    }
}