/*
 * Author: Sam Braude (Red ID: 826984009)
 * Intermediate Computer Programming Lab (CS 160L)
 * Erin Ratelle
 * June 28, 2023
 */

import java.util.ArrayList;
import java.util.List;

public class Espresso implements Coffee {
    @Override
    public double getCost() {
        return 1.75;
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Espresso");
        return ingredients;
    }

    @Override
    public String printCoffee() {
        return "An espresso";
    }
}

