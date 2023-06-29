/*
 * Author: Sam Braude (Red ID: 826984009)
 * Intermediate Computer Programming Lab (CS 160L)
 * Erin Ratelle
 * June 28, 2023
 */

import java.util.List;

public interface Coffee {
    /*
     * Baseline for both coffee options.
     */
    public double getCost();

    public List<String> getIngredients();

    public String printCoffee();
}
