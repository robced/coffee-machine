package machine;

public class CoffeeMachine {
    private int machineWater;
    private int machineMilk;
    private int machineBeans;
    private int machineCups;
    private int machineEarnings;

    private MachineStatus status;

    public CoffeeMachine(int machineWater, int machineMilk, int machineBeans, int machineCups, int machineEarnings) {
        this.machineWater = machineWater;
        this.machineMilk = machineMilk;
        this.machineBeans = machineBeans;
        this.machineCups = machineCups;
        this.machineEarnings = machineEarnings;

        setStartingState();
    }

    public void process(String userInput) {
        switch (status) {
            case MENU: {
                handleMenuChoice(userInput);
                break;
            }
            case BUYING: {
                buyCoffee(userInput);
                setStartingState();
                break;
            }
            case FILLING_WATER: {
                fillWater(userInput);
                break;
            }
            case FILLING_MILK: {
                fillMilk(userInput);
                break;
            }
            case FILLING_BEANS: {
                fillBeans(userInput);
                break;
            }
            case FILLING_CUPS: {
                fillCups(userInput);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void handleMenuChoice(String userChoice) {
        switch (userChoice) {
            case "buy": {
                System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                status = MachineStatus.BUYING;
                break;
            }
            case "fill": {
                System.out.println("\nWrite how many ml of water do you want to add:");
                status = MachineStatus.FILLING_WATER;
                break;
            }
            case "take": {
                withdrawEarnings();
                break;
            }
            case "remaining": {
                showInventory();
                break;
            }
            case "exit": {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Wrong input.");
            }
        }
    }

    private void buyCoffee(String coffeeType) {
        try {
            reduceInventory(coffeeType);
            System.out.println("I have enough resources, making you coffee!\n");
        } catch (Exception e) {
            System.out.println("Sorry, not enough " + e.getMessage() + "!\n");
        }
    }

    private void chargeCoffee(int coffeePrice) {
        machineEarnings += coffeePrice;
    }

    private void setStartingState() {
        status = MachineStatus.MENU;
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private void fillWater(String waterAmount) {
        machineWater += Integer.parseInt(waterAmount);
        System.out.println("Write how many ml of milk do you want to add:");
        status = MachineStatus.FILLING_MILK;
    }

    private void fillMilk(String milkAmount) {
        machineMilk += Integer.parseInt(milkAmount);
        System.out.println("Write how many grams of coffee beans do you want to add:");
        status = MachineStatus.FILLING_BEANS;
    }

    private void fillBeans(String beansAmount) {
        machineBeans += Integer.parseInt(beansAmount);
        System.out.println("Write how many disposable cups of coffee do you want to add");
        status = MachineStatus.FILLING_CUPS;
    }

    private void fillCups(String cupsAmount) {
        machineCups += Integer.parseInt(cupsAmount);

        System.out.println();
        setStartingState();
    }

    private void withdrawEarnings() {
        System.out.println("\nI gave you $" + machineEarnings);
        machineEarnings = 0;

        System.out.println();
        setStartingState();
    }

    private void showInventory() {
        System.out.println("\nThe coffee machine has:");
        System.out.printf("%d ml of water%n" +
                "%d ml of milk%n" +
                "%d g of coffee beans%n" +
                "%d disposable cups%n" +
                "$%d of money%n", machineWater, machineMilk, machineBeans, machineCups, machineEarnings);

        System.out.println();
        setStartingState();
    }

    private void reduceInventory(String coffeeType) throws Exception {
        Coffee coffee;

        switch (coffeeType) {
            case "1": {
                coffee = Coffee.EXPRESSO;
                break;
            }
            case "2": {
                coffee = Coffee.LATTE;
                break;
            }
            case "3": {
                coffee = Coffee.CAPPUCCINO;
                break;
            }
            case "back": {
                System.out.println();
                status = MachineStatus.MENU;
            }
            default: {
                System.out.println("Wrong input.");
                return;
            }
        }

        if (canMakeCoffee(coffee)) {
            machineWater -= coffee.getWaterNeeded();
            machineMilk -= coffee.getMilkNeeded();
            machineBeans -= coffee.getBeansNeeded();
            machineCups -= 1;
            chargeCoffee(coffee.getCoffeePrice());
        }
    }

    private boolean canMakeCoffee(Coffee coffee) throws Exception {
        if (machineWater < coffee.getWaterNeeded()) {
            throw new Exception("water");
        }

        if (machineMilk < coffee.getMilkNeeded()) {
            throw new Exception("milk");
        }

        if (machineBeans < coffee.getBeansNeeded()) {
            throw new Exception("beans");
        }

        if (machineCups < 1) {
            throw new Exception("reusable cups");
        }

        return true;
    }
}