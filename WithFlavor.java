/*
 * Author: Sam Braude (Red ID: 826984009)
 * Intermediate Computer Programming Lab (CS 160L)
 * Erin Ratelle
 * June 28, 2023
 */

import java.util.ArrayList;
import java.util.List;

public class WithFlavor extends CoffeeDecorator {

    /*
     * Using an enum for syrup flavors saves time so that there is no need
     * to type out every type of syrup.
     */
    enum Syrup {
        CARAMEL,
        MOCHA,
        VANILLA
    }

    private Syrup flavor;

    public WithFlavor(Coffee c, Syrup s) {
        super(c);
        flavor = s;
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.35;
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(flavor.name() + " Syrup");
        return ingredients;
    }

    @Override
    public String printCoffee() {
        return super.printCoffee() + " with " + flavor;
    }
}
