import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class Main {

    // The file where all expenses are saved
    static String fileName = "expenses.txt";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean keepRunning = true;

        // Keep showing the menu until the user chooses to exit
        while (keepRunning) {

            // Show the menu options
            System.out.println("\n=== EXPENSE TRACKER ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Spending");
            System.out.println("4. Category Summary");
            System.out.println("5. Monthly Summary");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            // Read what the user typed
            int choice = input.nextInt();
            input.nextLine(); // Clear the leftover newline after the number

            // Run the correct method based on the user's choice
            if (choice == 1) {
                addExpense(input);
            } else if (choice == 2) {
                viewExpenses();
            } else if (choice == 3) {
                totalSpending();
            } else if (choice == 4) {
                categorySummary();
            } else if (choice == 5) {
                monthlySummary();
            } else if (choice == 6) {
                keepRunning = false; // This stops the while loop
            } else {
                System.out.println("Invalid choice! Please pick a number from 1 to 6.");
            }
        }

        System.out.println("Goodbye!");
    }


    
    // Asks the user for expense details and saves them to the file
    public static void addExpense(Scanner input) {

        // Ask what the expense was for
        System.out.print("Enter description: ");
        String description = input.nextLine();

        // Ask which category it belongs to
        System.out.print("Enter category (Food/Transport/Entertainment/Other): ");
        String category = input.nextLine();

        // Ask how much it cost
        System.out.print("Enter amount: ");
        double amount = input.nextDouble();

        // Get today's date automatically
        LocalDate today = LocalDate.now();

        // Try to open the file and write the new expense
        try {
            // 'true' means we ADD to the file instead of overwriting it
            FileWriter fileWriter = new FileWriter(fileName, true);

            // Write one line in this format: date,description,category,amount
            fileWriter.write(today + "," + description + "," + category + "," + amount + "\n");

            // Always close the file when done writing
            fileWriter.close();

            System.out.println("Expense saved successfully!");

        } catch (IOException error) {
            // This runs if something went wrong with saving the file
            System.out.println("Oops! Could not save the expense.");
        }
    }


   
    // Reads the file and prints every saved expense on screen
    public static void viewExpenses() {

        // Check if the file exists before trying to open it
        File file = new File(fileName);

        if (file.exists() == false) {
            System.out.println("No expenses found. Add one first!");
            return; // Stop the method here
        }

        System.out.println("\n--- Your Expenses ---");

        // Track whether we printed at least one expense
        boolean foundAnyExpense = false;

        try {
            Scanner fileReader = new Scanner(file);

            // Read the file one line at a time
            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine();

                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Each line looks like: date,description,category,amount
                // Split it into separate pieces using the comma
                String[] parts = line.split(",");

                // Make sure the line has all 4 pieces before using it
                if (parts.length < 4) {
                    continue; // Skip broken lines
                }

                // Give each piece a clear name
                String date        = parts[0];
                String description = parts[1];
                String category    = parts[2];
                String amount      = parts[3];

                // Print the expense in a readable format
                System.out.println("Date: " + date + " | Item: " + description + " | Category: " + category + " | Amount: R" + amount);

                foundAnyExpense = true;
            }

            fileReader.close();

            // Let the user know if nothing was printed
            if (foundAnyExpense == false) {
                System.out.println("No expenses found.");
            }

        } catch (Exception error) {
            System.out.println("Oops! Could not read the expenses file.");
        }
    }


  
    // Adds up every expense and prints the grand total
    public static void totalSpending() {

        File file = new File(fileName);

        if (file.exists() == false) {
            System.out.println("No expenses found. Add one first!");
            return;
        }

        // Start the total at zero
        double total = 0;

        try {
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine();

                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split the line into parts
                String[] parts = line.split(",");

                // Need at least 4 parts to get the amount
                if (parts.length < 4) {
                    continue;
                }

                // The amount is the 4th piece (index 3)
                // parseDouble converts text like "50.0" into the number 50.0
                double amount = Double.parseDouble(parts[3]);

                // Add this expense to the running total
                total = total + amount;
            }

            fileReader.close();

            System.out.println("Total Spending: R" + total);

        } catch (Exception error) {
            System.out.println("Oops! Could not calculate the total.");
        }
    }


    
    // Reads all expenses and adds them up per category
    public static void categorySummary() {

        File file = new File(fileName);

        if (file.exists() == false) {
            System.out.println("No expenses found. Add one first!");
            return;
        }

        // Each category starts at zero
        double foodTotal          = 0;
        double transportTotal     = 0;
        double entertainmentTotal = 0;
        double otherTotal         = 0;

        try {
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine();

                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length < 4) {
                    continue;
                }

                // toLowerCase() makes matching work even if user typed "Food" or "FOOD"
                String category = parts[2].toLowerCase();
                double amount   = Double.parseDouble(parts[3]);

                // Add the amount to the correct category total
                if (category.equals("food")) {
                    foodTotal = foodTotal + amount;
                } else if (category.equals("transport")) {
                    transportTotal = transportTotal + amount;
                } else if (category.equals("entertainment")) {
                    entertainmentTotal = entertainmentTotal + amount;
                } else {
                    // Anything that doesn't match a known category goes into Other
                    otherTotal = otherTotal + amount;
                }
            }

            fileReader.close();

            // Print the total for each category
            System.out.println("\n--- CATEGORY SUMMARY ---");
            System.out.println("Food:          R" + foodTotal);
            System.out.println("Transport:     R" + transportTotal);
            System.out.println("Entertainment: R" + entertainmentTotal);
            System.out.println("Other:         R" + otherTotal);

        } catch (Exception error) {
            System.out.println("Oops! Could not calculate the category summary.");
        }
    }


    // Groups expenses by month and prints the total spent in each month
    public static void monthlySummary() {

        File file = new File(fileName);

        if (file.exists() == false) {
            System.out.println("No expenses found. Add one first!");
            return;
        }

        // A HashMap works like a table with two columns: month name and total amount
        // Example: "2025-04" -> 350.0
        HashMap<String, Double> monthlyTotals = new HashMap<String, Double>();

        try {
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine();

                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length < 4) {
                    continue;
                }

                // The date looks like: 2025-04-10
                
                String fullDate  = parts[0];
                String month     = fullDate.substring(0, 7);
                double amount    = Double.parseDouble(parts[3]);

                // Check if we already have a total for this month
                if (monthlyTotals.containsKey(month)) {
                    // Month already exists  add the new amount to the existing total
                    double currentTotal = monthlyTotals.get(month);
                    monthlyTotals.put(month, currentTotal + amount);
                } else {
                    // Month is new  add it to the table with this as the first amount
                    monthlyTotals.put(month, amount);
                }
            }

            fileReader.close();

            // Print each month and its total
            System.out.println("\n--- MONTHLY SUMMARY ---");

            for (String month : monthlyTotals.keySet()) {
                double total = monthlyTotals.get(month);
                System.out.println(month + " : R" + total);
            }

        } catch (Exception error) {
            System.out.println("Oops! Could not generate the monthly summary.");
        }
    }
}