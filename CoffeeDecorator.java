/*
 * Author: Sam Braude (Red ID: 826984009)
 * Intermediate Computer Programming Lab (CS 160L)
 * Erin Ratelle
 * June 28, 2023
 */

import java.util.List;

public abstract class CoffeeDecorator implements Coffee {
    /*
     * Baseline for all extra ingredients.
     */
    private Coffee coffee;
    /*
     * Initialize a new coffee for order.
     */
    public CoffeeDecorator(Coffee c) {
        coffee = c;
    }
    /*
     * Returns the cost of the coffee. This is used
     * to properly construct the user's order and
     * to write to the OrderLog.txt file.
     */
    public double getCost() { return coffee.getCost(); }

    /*
     * Contains the ingredients inside the coffee/order
     */
    public List<String> getIngredients() {
        return null;
    }
    /*
     * Print a description of what the ingredient is.
     * This is used to properly construct the user's order
     * and to write to the OrderLog.txt file.
     */
    public String printCoffee() {
        return coffee.printCoffee();
    }
}
