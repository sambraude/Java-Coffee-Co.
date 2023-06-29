/*
 * Author: Sam Braude (Red ID: 826984009)
 * Intermediate Computer Programming Lab (CS 160L)
 * Erin Ratelle
 * June 28, 2023
 */

import java.util.ArrayList;
import java.util.List;

public class WithSugar extends CoffeeDecorator {
    public WithSugar(Coffee c) {
        super(c);
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.15;
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Sugar");
        return ingredients;
    }

    @Override
    public String printCoffee() {
        return super.printCoffee() + " with sugar";
    }
}
