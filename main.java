package newTry;

import java.util.Scanner;

enum MachineStatus {
    MAIN_MENU,
    SELLING,
    REFILLING
}

enum RefillIngr {
    WATER,
    MILK,
    BEANS,
    CUPS,
    PROMPT
}

enum CoffeeType {
    PROMPT,
    MENU,
    ESPRESSO,
    LATTE,
    CAPPUCCINO
}

enum LackOfIngr {
    WATER,
    MILK,
    BEANS,
    CUPS,
    NONE
}

class CoffeeMachine {
    
    private int money;
    private int water;
    private int milk = 540;
    private int beans = 120;
    private int dispCups = 9;
    private MachineStatus machineStatus;
    private RefillIngr refillIngr;
    private CoffeeType coffeeType;
    private LackOfIngr lackOfIngr;
	
	public Scanner scanner;
     
    public CoffeeMachine(Scanner scanner) {
        this.money = 550;
        this.water = 400;
        this.milk = 540;
        this.beans = 120;
        this.dispCups = 9;
        this.machineStatus = MachineStatus.MAIN_MENU;
        this.refillIngr = RefillIngr.PROMPT;
        this.coffeeType = CoffeeType.PROMPT;
        this.lackOfIngr = LackOfIngr.NONE;
		this.scanner = scanner;
    }

    private void collectMoney() {

        System.out.println("I gave you $" + this.money + "\n");
        this.money = 0;
        this.machineStatus = MachineStatus.MAIN_MENU;
    }
    
    private void remainigIngridients() {

        System.out.println("\nThe coffee machine has:\n" + this.water + " ml of water\n" 
                            + this.milk + " ml of milk\n" + this.beans + " g of coffee beans\n" 
                                + this.dispCups + " disposable cups\n$" + this.money + " of money\n");
        this.machineStatus = MachineStatus.MAIN_MENU;
    }

    private boolean checkEnoughIngredients() {

        switch (this.coffeeType) {
            case ESPRESSO:
                if (this.water < 250) {
                    this.lackOfIngr = LackOfIngr.WATER;
                } else if (this.beans < 16) {
                    this.lackOfIngr = LackOfIngr.BEANS;
                } else if (this.dispCups < 1) {
                    this.lackOfIngr = LackOfIngr.CUPS;
                }
                break;
            case LATTE:
                if (this.water < 350) {
                    this.lackOfIngr = LackOfIngr.WATER;
                } else if (this.milk < 75) {
                    this.lackOfIngr = LackOfIngr.MILK;
                } else if (this.beans < 20) {
                    this.lackOfIngr = LackOfIngr.BEANS;
                } else if (this.dispCups < 1) {
                    this.lackOfIngr = LackOfIngr.CUPS;
                } 
                break;
            case CAPPUCCINO:
                if (this.water < 200) {
                    this.lackOfIngr = LackOfIngr.WATER;
                } else if (this.milk < 100) {
                    this.lackOfIngr = LackOfIngr.MILK;
                } else if (this.beans < 12) {
                    this.lackOfIngr = LackOfIngr.BEANS;
                } else if (this.dispCups < 1) {
                    this.lackOfIngr = LackOfIngr.CUPS;
                } 
                break;
        }
        return this.lackOfIngr == LackOfIngr.NONE ? true : false;
    }

    private void printLackState() {

        System.out.println("Sorry, not enough " + this.lackOfIngr.name().toLowerCase() + "!\n");
        this.lackOfIngr = LackOfIngr.NONE;
        this.coffeeType = CoffeeType.PROMPT;

    }
    
    private void makeCoffee() {

        switch (this.coffeeType) {
            case ESPRESSO:
                if (!(checkEnoughIngredients())) {
                    printLackState();
                    return;
                }
                water -= 250;
                beans -= 16;
                dispCups -= 1;
                money += 4;
                break;
            case LATTE:
                if (!(checkEnoughIngredients())) {
                    printLackState();
                    return;
                }
                water -= 350;
                milk -= 75;
                beans -= 20;
                dispCups -= 1;
                money += 7;
                break;
            
            case CAPPUCCINO:
                if (!(checkEnoughIngredients())) {
                    printLackState();
                    return;
                }
                water -= 200;
                milk -= 100;
                beans -= 12;
                dispCups -= 1;
                money += 6;
                break;
        }
        System.out.println("I have enough resources, making you a coffee!\n");
        this.coffeeType = CoffeeType.PROMPT;
        
    }
    
    private void chooseCoffee(String input) {

        if ("back".equals(input)) {
            this.machineStatus = MachineStatus.MAIN_MENU;
            this.coffeeType = CoffeeType.PROMPT;
            System.out.println();
            return;
        }
        switch (this.coffeeType) {
            case PROMPT:
                System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                this.coffeeType = CoffeeType.MENU;
                break;
            case MENU:
                if ("1".equals(input)) {
                    this.coffeeType = CoffeeType.ESPRESSO;
                } else if ("2".equals(input)) {
                    this.coffeeType = CoffeeType.LATTE;
                } else if (("3".equals(input))) {
                    this.coffeeType = CoffeeType.CAPPUCCINO;
                } else {
                    System.out.println("Incorrect input! Try again!");
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    return;
                }
                makeCoffee();
                this.lackOfIngr = LackOfIngr.NONE;
                this.machineStatus = MachineStatus.MAIN_MENU;
                break;
        }
    }
    
    private void refillIngredients(String input) {
        switch (this.refillIngr) {
            case PROMPT:
                System.out.println("\nWrite how many ml of water you want to add:");
                this.refillIngr = RefillIngr.WATER;
                break;
            case WATER:
                this.water += Integer.parseInt(input);
                System.out.println("Write how many ml of milk you want to add:");
                this.refillIngr = RefillIngr.MILK;
                break;
            case MILK:
                this.milk += Integer.valueOf(input);
                System.out.println("Write how many grams of coffee beans you want to add:");
                this.refillIngr = RefillIngr.BEANS;
                break;
            case BEANS:
                this.beans += Integer.parseInt(input);
                System.out.println("Write how many disposable cups of coffee you want to add:");
                this.refillIngr = RefillIngr.CUPS;
                break;
            case CUPS:
                this.dispCups += Integer.parseInt(input);
                System.out.println();
                this.refillIngr = RefillIngr.PROMPT;
                this.machineStatus = MachineStatus.MAIN_MENU;
                break;
        }
    }

	private void checkMachineStatus(String userInput) {
        
        switch (this.machineStatus) {
            case MAIN_MENU:
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                break;
            case SELLING:
                chooseCoffee(userInput);
                break;
            case REFILLING:
                refillIngredients(userInput);
                break;
        }
	}
    
    private void checkUserAction(String userInput) {

        if ("exit".equals(userInput)) {
            System.exit(0);
        } else if ("buy".equals(userInput)) {
            this.machineStatus = MachineStatus.SELLING;
            chooseCoffee(userInput);
        } else if ("fill".equals(userInput)) {
            this.machineStatus = MachineStatus.REFILLING;
            refillIngredients(userInput);
        } else if ("take".equals(userInput)) {
            collectMoney();
        } else if ("remaining".equals(userInput)) {
            remainigIngridients();
        } else {
                System.out.println("Incorrect input! Try again!\n");
        }
    }

	public void readUserInput() {

        String userInput = new String();

        while (true) {
            if (machineStatus == MachineStatus.MAIN_MENU) {
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                userInput = this.scanner.nextLine();
                checkUserAction(userInput);
            } else {
                userInput = this.scanner.nextLine();
                checkMachineStatus(userInput);
            } 
        }
	}
}

public class main {
	public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine(scanner);

		coffeeMachine.readUserInput();
	}
}
