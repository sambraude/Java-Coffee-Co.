/*
 * Author: Sam Braude (Red ID: 826984009)
 * Intermediate Computer Programming Lab (CS 160L)
 * Erin Ratelle
 * June 28, 2023
 */

import java.io.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class Main {
    private static Map<String, Integer> inventory = new TreeMap<String, Integer>();
    private static List<CoffeeOrder> orders = new ArrayList<CoffeeOrder>();
    private static Scanner input = new Scanner(System.in);
    private static String logFile = "OrderLog.txt";
    private static String inventoryFile = "Inventory.txt";
    private static String ratingFile = "Rating.txt";
    private static double average;

    /*
     * We start the main() by calculating the rating for the next order.
     * Since the rating can be different for each order, and we display
     * our rating at the start of the program, we calculate it before the user places
     * an order. The double average simply reads from the Rating.txt file and calculates
     * the average of all the ratings recorded. Then, the order process begins.
     * We continue asking if the user wants to place another order until they enter an 'N',
     * which will stop the loop and start the ending process where the inventory and OrderLog gets updated.
     * Then, the rating process begins.
    */
    public static void main(String[] args) {
        try {
            int total = 0;
            Scanner rating = new Scanner(new File("Rating.txt"));
            while (rating.hasNextInt()) {
                average += rating.nextInt();
                total++;
            }

            rating.close();
            average /= total;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        inventory = readInventory(inventoryFile);
        System.out.println("Welcome to Java Coffee Co.!");
        System.out.print("--- RATED ");
        System.out.printf("Value: %.2f", average);
        System.out.println(" STARS ---");
        try {
            boolean addOrder;
            do {
                CoffeeOrder order = buildOrder();
                if (order != null) {
                    orders.add(order);
                    System.out.println(order.printOrder());
                }
                System.out.println("\nWould you like to enter another order (Y or N)?");
                String yn = input.nextLine();
                while (!(yn.equalsIgnoreCase("N") || yn.equalsIgnoreCase("Y"))) {
                    System.out.println("Please enter Y or N.");
                    yn = input.nextLine();
                }
                addOrder = !yn.equalsIgnoreCase("N");
            } while (addOrder);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (orders.size() > 0) {
            writeOrderLog(logFile);
            writeInventory(inventoryFile);
            initiateReview();
        }
    }

    /*
     * This is the rating function. It prompts the user for a rating from 1-5 based on their experience
     * with the online ordering system. We first ask the user for an input, which gets converted into an int
     * to be compared with integers in the conditional statements. The while loop just ensures that the user
     * enters a valid input. Once the input is valid, a message will be displayed corresponding with the rating
     * from the user. The program will then save that rating by writing it to the rating.txt file for future use.
     * Future calculations for the average/overall rating will include this new rating.
     */
    private static void initiateReview() {
        int review = 0;
        System.out.println("Thank you for stopping by!");
        System.out.println("We would love to get some feedback about our services!");
        System.out.println("Please enter a rating from 1-5 (1 - Disliked, 5 - Enjoyed):");
        review = Integer.parseInt(input.nextLine());

        while (review < 1 || review > 5) {
            System.out.println("Please enter a valid rating.");
            System.out.println("Please enter a rating from 1-5 (1 - Disliked, 5 - Enjoyed):");
            review = Integer.parseInt(input.nextLine());
        }

        if (review >= 3) {
            System.out.println("Thank you! We are glad you were satisfied with our service!");
        } else {
            System.out.println("Thank you for your feedback.");
        }

        try (BufferedWriter ratingUpdate = new BufferedWriter(new FileWriter("rating.txt", true))) {
            ratingUpdate.write(String.valueOf(review));
            ratingUpdate.newLine();
            System.out.println("Rating update saved successfully.");
        } catch (Exception e) {
            System.out.println("Error writing rating update: " + e.getMessage());
        }
        input.close();
    }

    /*
     * This is what builds each order. The user will continuously be asked if they
     * would like another coffee until they enter 'N', which will end the loop and the order.
     * The beginning of the order starts with a choice of Black coffee or Espresso. Since you
     * can only have one coffee per order, only one coffee object is made. As the user makes
     * their selections, the updateInventory(String) function is called to update the inventory as
     * the user is making their coffee. Since there are many options for customization, a switch case
     * is used to increase efficiency and organization. Each number corresponds to an ingredient and
     * depending on the number picked the corresponding ingredient will be updated in the inventory.
     */
    private static CoffeeOrder buildOrder() {
        CoffeeOrder order = new CoffeeOrder();
        try {
            Scanner input = new Scanner(System.in);
            boolean addCoffee = true;
            while (addCoffee) {

                System.out.println("Select coffee type:");
                System.out.println("\t1. Black Coffee");
                System.out.println("\t2. Espresso");
                Coffee coffee;

                int option = 0;
                while (option < 1 || option > 2) {
                    if (!input.hasNextInt()) {
                        System.out.println("Please enter a valid number.");
                        input.nextLine();
                    } else {
                        option = input.nextInt();
                        if (option < 1 || option > 2) System.out.println("Please enter a valid option.");
                    }
                }
                input.nextLine();
                if (option == 2) {
                    updateInventory("Espresso");
                    coffee = new Espresso();
                } else {
                    updateInventory("Black Coffee");
                    coffee = new BlackCoffee();
                }

                while (option != 0) {
                    System.out.println(String.format("Coffee brewing: %s.", coffee.printCoffee()));
                    System.out.println("Would you like to add anything to your coffee?");
                    System.out.println("\t1. Flavored Syrup");
                    System.out.println("\t2. Hot Water");
                    System.out.println("\t3. Milk");
                    System.out.println("\t4. Sugar");
                    System.out.println("\t5. Whipped Cream");
                    System.out.println("\t0. NO - Finish Coffee");

                    while (!input.hasNextInt()) {
                        System.out.println("Please enter a valid number.");
                        input.nextLine();
                    }
                    option = input.nextInt();
                    input.nextLine();

                    coffee = switch (option) {
                        case 1 -> {
                            System.out.println("Please select a flavor:");
                            System.out.println("1. CARAMEL");
                            System.out.println("2. MOCHA");
                            System.out.println("3. VANILLA");
                            int check = input.nextInt();

                            while(check < 0 && check > 4) {
                                System.out.println("Invalid Choice");
                                System.out.println("Please select a flavor:");
                                System.out.println("1. CARAMEL");
                                System.out.println("2. MOCHA");
                                System.out.println("3. VANILLA");
                                check = input.nextInt();
                            }

                            WithFlavor.Syrup flavor = WithFlavor.Syrup.values()[check - 1];
                            updateInventory(flavor.name() + " Syrup");
                            option = 1;
                            yield new WithFlavor(coffee, flavor);
                        }
                        case 2 -> {
                            updateInventory("Hot Water");
                            yield new WithHotWater(coffee);
                        }
                        case 3 -> {
                            updateInventory("Milk");
                            yield new WithMilk(coffee);
                        }
                        case 4 -> {
                            updateInventory("Sugar");
                            yield new WithSugar(coffee);
                        }
                        case 5 -> {
                            updateInventory("Whipped Cream");
                            yield new WithWhippedCream(coffee);
                        }
                        default -> {
                            if (option != 0) System.out.println("Please enter valid option.");
                            yield coffee;
                        }
                    };
                }
                order.addCoffee(coffee);

                System.out.println("Would you like to order another coffee (Y or N)?");
                String yn = input.nextLine();
                while (!(yn.equalsIgnoreCase("N") || yn.equalsIgnoreCase("Y"))) {
                    System.out.println("Please enter Y or N.");
                    yn = input.nextLine();
                }
                addCoffee = !yn.equalsIgnoreCase("N");
            }
        } catch (Exception e) {
            System.out.println("Error building order: " + e.getMessage());
        }
        return order;
    }

    /*
     * This reads the inventory to properly deduct the ingredients used in an order
     * to properly keep track of what is in stock and what isn't. It separates the lines
     * around the = to trim out the name of the ingredient and the count on hand.
     */
    private static Map<String, Integer> readInventory(String inventoryFile) {
        Map<String, Integer> inventory = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inventoryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String ingredient = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    inventory.put(ingredient, quantity);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory: " + e.getMessage());
        }
        return inventory;
    }

    /*
     * This is what updates the inventory. If the ingredient is on the file,
     * the count will be reduced by the number of ingredients in the order. If the
     * ingredient is out of stock, it will print that we are out of stock
     */

    private static void updateInventory(String ingredient) {
        if (inventory.containsKey(ingredient)) {
            int quantity = inventory.get(ingredient);
            if (quantity > 0) {
                inventory.put(ingredient, quantity - 1);
            } else {
                System.out.println("Sorry, we are out of " + ingredient);
            }
        }
    }

    /*
     * This is the function that writes and officially updates
     * the file based on the results from updateInventory().
     */
    private static void writeInventory(String inventoryFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inventoryFile))) {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                String ingredient = entry.getKey();
                int quantity = entry.getValue();
                writer.write(ingredient + " = " + quantity);
                writer.newLine();
            }
            System.out.println("Inventory updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing inventory: " + e.getMessage());
        }
    }

    /*
     * This prints the order that the customer places. After the customer
     * finishes an order, the details get written to OrderLog.txt and this
     * pulls data from the most recent orders and displays it in the user interface.
     */
    private static void writeOrderLog(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (CoffeeOrder order : orders) {
                writer.write(order.printOrder());
                writer.newLine();
            }
            orders.clear();
        } catch (Exception e) {
            System.out.println("Error writing order log: " + e.getMessage());
        }
    }
}
